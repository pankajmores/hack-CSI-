


import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link,Navigate } from 'react-router-dom';
import './App.css'; // Import CSS file
import Login from './login'; // Import your Login component
import { useState } from 'react';
import CoursePage from './components/course';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const handleLogin = (username, password) => {
    // In a real app, you'll call an API here to authenticate
    if (username === 'user' && password === 'password') {
      setIsAuthenticated(true);
    }
  };
  return (
    <Router>
      <div className="App">
        {/* Header */}
        <header className="header">
          <div className="logo">Intern Expert Training</div>
          <nav>
          <ul>
  <li><Link to="/home"><button className="nav-btn">Home</button></Link></li>
  <li><Link to="/about"><button className="nav-btn">About</button></Link></li>
  <li><Link to="/courses"><button className="nav-btn">Courses</button></Link></li>
  <li><Link to="/mentors"><button className="nav-btn">Mentors</button></Link></li>
  <li><Link to="/contact"><button className="nav-btn">Contact</button></Link></li>
  <li><Link to="/login"><button className="nav-btn">Login</button></Link></li> {/* Link to Login */}
</ul>

          </nav>
        </header>

        {/* Hero Section */}
        <section className="hero">
          <div className="hero-content">
            <h1>Empower Your Career with Expert-Led Intern Training</h1>
            <p>Learn from the best, gain practical experience, and accelerate your career!</p>
            <button>Explore Courses</button>
            <button>Enroll Now</button>
          </div>
        </section>

        { <section className="flip-cards">
           <div className="card-container">
            {['Course 1', 'Course 2', 'Course 3', 'Course 4'].map((course, index) => (
              <Link key={index} to={`/course${index + 1}`} className="card">
                <div className="card-inner">
                  <div className="card-front">
                    <h2>{course}</h2>
                  </div>
                  <div className="card-back">
                    <p>Learn more about {course}</p>
                  </div>
                </div>
              </Link>
            ))}
          </div>
        </section> }

        {/* Routes */}
        <Routes>
          <Route path="/home" element={<h2>Welcome to Home</h2>} />
          <Route path="/about" element={<h2>About Us</h2>} />
          <Route path="/courses" element={<h2>Courses</h2>} />
          <Route path="/mentors" element={<h2>Mentors</h2>} />
          <Route path="/contact" element={<h2>Contact</h2>} />
          <Route path="/login" element={<Login />} /> {/* Update this to render the Login component */}
        </Routes>
        <Route path="/course">
          {isAuthenticated ? <CoursePage /> : <Navigate to="/login" />}
        </Route>
        {/* Footer */}
        <footer>
          <p>© 2025 Intern Expert Training. All rights reserved.</p>
        </footer>
      </div>
    </Router>
  );
}

export default App;
