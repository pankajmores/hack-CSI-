import React from "react";
import "./Header.css";

const Header = () => {
  return (
    <header className="header">
      <div className="logo">Intern Expert Training</div>
      <nav>
        <ul className="nav-links">
          <li><a href="#home">Home</a></li>
          <li><a href="#about">About</a></li>
          <li><a href="#courses">Courses</a></li>
          <li><a href="#mentors">Mentors</a></li>
          <li><a href="#contact">Contact</a></li>
          <li><a href="#login">Login</a></li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
