import React, {useEffect, useReducer} from "react";
import Header from "./components/header";
import RouteMapper from "./routeMapper";
import Footer from "./components/footer";
import {BrowserRouter as Router} from "react-router-dom";
import {strings} from "./i18n/i18n";
import {useAppSelector} from "./hooks";


export default function Startup() {
    const lang = useAppSelector((state) => state.i18n.lang)
    const [ignored, forceUpdate] = useReducer(x => x + 1, 0);

    useEffect(() => {
        strings.setLanguage(lang)
        forceUpdate()

    }, [lang])
    return (
        <div>
            <Router>
                <Header/>
                <RouteMapper/>
                <Footer/>
            </Router>
        </div>
    )
}