

import { Routes, Route } from "react-router-dom";
import  Home  from "./pages/home";
import  Users  from "./pages/users";
import  About  from "./pages/about";

function RouteMapper() {
  return (    
      <div>
        <Routes>
          <Route path="/about" element={<About />}/>
          <Route path="/users" element={<Users />}/>
          <Route path="/" element={<Home />}/>
        </Routes>
      </div>
  );
}

export default RouteMapper;