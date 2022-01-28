import React, {useContext} from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import Navigation from "./components/Navigation";
import Home from "./components/Home";
import Login from "./components/Login";
import AuthContext from './store/auth_context';

function App() {
  const authContext = useContext(AuthContext);

  return (
    <div className="bg-black bg-opacity-10 vh-100 overflow-auto">
      <BrowserRouter>
        <div className="mb-5">
          <Navigation />
        </div>

        <Routes>
          {authContext.isLoggedIn && <Route path="/" element={<Home />} />}
          <Route path="/login" element={<Login />} />
          {!authContext.isLoggedIn && <Route path="*" element={<Navigate to="/login" />} />}
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
