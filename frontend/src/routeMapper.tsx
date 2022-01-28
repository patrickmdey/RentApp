import {Route, Routes} from "react-router-dom";
import Home from "./pages/home";
import About from "./pages/about";
import Article from "./pages/Article";
import Login from "./pages/Login";

export default function RouteMapper() {
    return (
        <div>
            <Routes>
                <Route path="/about" element={<About/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/" element={<Home/>}/>
                <Route path={`/articles/:id`} element={<Article/>}/>
            </Routes>
        </div>
    );
}

