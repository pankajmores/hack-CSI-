


// login.js
import React, { useState } from 'react';
import './login.css';  // Import the login styles here

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(true); // Track if modal is open

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Logging in with:', email, password);
    // You can add login logic here
  };

  const handleClose = () => {
    setIsModalOpen(false); // Close the modal when the close button is clicked
  };

  return (
    isModalOpen && (
      <div className="login-overlay active">
        <div className="login-box">
          <button className="close-btn" onClick={handleClose}>×</button>
          <h2>Login</h2>
          <form onSubmit={handleSubmit}>
            <div>
              <label>Email:</label>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                placeholder="Enter your email"
              />
            </div>
            <div>
              <label>Password:</label>
              <input
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                placeholder="Enter your password"
              />
            </div>
            <div className="remember-me">
              <label>
                <input type="checkbox" /> Remember Me
              </label>
              <a href="/forgot-password">Forgot Password?</a>
            </div>
            <button type="submit" className="submit-btn">Login</button>
          </form>
          <div className="register-link">
            <p>Don't have an account? <a href="/register">Register</a></p>
          </div>
        </div>
      </div>
    )
  );
}

export default Login;




