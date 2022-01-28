import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Navigation from "./components/Navigation";
import Home from "./components/Home";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Navigation />

        <Routes>
          <Route path="/" element={<Home />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
