// Required Modules
const http = require('http');
const mongoose = require('mongoose');
const { MongoClient, ServerApiVersion } = require('mongodb');
const dotenv = require('dotenv');
const cors = require('cors');
const helmet = require('helmet'); // Security best practices
const path = require('path');
const morgan = require('morgan'); // Optional for logging HTTP requests
const bodyParser = require('body-parser'); // Parsing JSON requests
const Grid = require('gridfs-stream'); // File storage
const multer = require('multer'); // File upload middleware
const axios = require('axios');
const  express  = require("express");
const { v4: uuidv4 } = require('uuid');


dotenv.config();

// Create an Express app
const app = express();
const server = http.createServer(app);

// Middleware
app.use(cors());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(helmet()); // Adding security headers
app.use(morgan('tiny')); 
const PORT = 3000;

// Middleware
app.use(bodyParser.json());

// API Key for WorqHat (Replace with your actual API key)
const apiKey = 'sk-36ea09703f22462cbea8a1d51a97e154';
const uri = "mongodb+srv://letapdhruv782808:dhruv@123@internhack.e58mk.mongodb.net/?retryWrites=true&w=majority&appName=internhack";
// Create a MongoClient with a MongoClientOptions object to set the Stable API version
const client = new MongoClient(uri, {
  serverApi: {
    version: ServerApiVersion.v1,
    strict: true,
    deprecationErrors: true,
  }
});
async function run() {
  try {
    // Connect the client to the server	(optional starting in v4.7)
    await client.connect();
    // Send a ping to confirm a successful connection
    await client.db("admin").command({ ping: 1 });
    console.log("Pinged your deployment. You successfully connected to MongoDB!");
  } finally {
    // Ensures that the client will close when you finish/error
    await client.close();
  }
}
run().catch(console.dir);
const chatSchema = new mongoose.Schema({
    chatId: { type: String, required: true, unique: true },
    question: { type: String, required: true },
    reply: { type: String, required: true },
    timestamp: { type: Date, default: Date.now }
  });
// Chat route
// app.post('/chat', async (req, res) => {
//     const { question } = req.body;

//     if (!question) {
//         return res.status(400).send({ error: "Question is required" });
//     }

//     // Payload for the API request
//     const payload = {
//         question,
//         model: "aicon-v4-nano-160824",
//         randomness: 0.5,
//         stream_data: false,
//         training_data: "You can define your system instructions or context here.",
//         response_type: "text",
//     };

//     const requestOptions = {
//         method: 'POST',
//         url: 'https://api.worqhat.com/api/ai/content/v4',
//         headers: {
//             "Content-Type": "application/json",
//             "Authorization": `Bearer ${apiKey}`,
//         },
//         data: payload,
//     };

//     try {
//         // Send request to WorqHat API
//         const response = await axios(requestOptions);
//         const reply = response.data?.content || "No response from AI.";

//         // Return AI's reply to the client
//         res.send({ reply });
//     } catch (error) {
//         console.error("Error while fetching data from WorqHat API:", error.message);
//         res.status(500).send({
//             error: "Failed to fetch data from AI model",
//             details: error.message,
//         });
//     }
// });

// Start the server
// Chat route
app.post('/chat', async (req, res) => {
    const { question } = req.body;

    if (!question) {
        return res.status(400).send({ error: "Question is required" });
    }

    // Generate a unique UUID for this session
    const chatId = uuidv4();

    // Payload for the API request
    const payload = {
        question,
        model: "aicon-v4-nano-160824",
        randomness: 0.5,
        stream_data: false,
        training_data: "You can define your system instructions or context here.",
        response_type: "text",
    };

    const requestOptions = {
        method: 'POST',
        url: 'https://api.worqhat.com/api/ai/content/v4',
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${apiKey}`,
        },
        data: payload,
    };

    try {
        // Send request to WorqHat API
        const response = await axios(requestOptions);
        const reply = response.data?.content || "No response from AI.";

        // Save to MongoDB
        const newChat = new Chat({ chatId, question, reply });
        await newChat.save();

        // Return AI's reply along with the chatId for future reference
        res.send({ chatId, reply });
    } catch (error) {
        console.error("Error while fetching data from WorqHat API:", error.message);
        res.status(500).send({
            error: "Failed to fetch data from AI model",
            details: error.message,
        });
    }
});

// Get chat history by ID
app.get('/chat/:id', async (req, res) => {
    const { id } = req.params;
    try {
        const chat = await Chat.findOne({ chatId: id });
        if (!chat) {
            return res.status(404).send({ error: "Chat not found" });
        }
        res.send(chat);
    } catch (err) {
        res.status(500).send({ error: "Error retrieving chat" });
    }
});

// Start the server
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
