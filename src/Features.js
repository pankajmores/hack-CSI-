import React from "react";
import "./Features.css";

const Features = () => {
  const features = [
    "Expert Mentors",
    "Hands-On Projects",
    "Career Support",
    "Flexible Learning Options"
  ];

  return (
    <section className="features">
      <h2>Why Choose Us?</h2>
      <div className="features-grid">
        {features.map((feature, index) => (
          <div key={index} className="feature-card">
            {feature}
          </div>
        ))}
      </div>
    </section>
  );
};

export default Features;
