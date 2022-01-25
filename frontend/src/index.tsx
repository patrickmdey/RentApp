import React from "react";
import ReactDOM from "react-dom";
import "./css/index.css";
import { BrowserRouter as Router } from "react-router-dom";
import { Provider } from "react-redux";
import { store } from "./store";

import Header from "./components/header";
import RouteMapper from "./routeMapper";

import "bootstrap/dist/css/bootstrap.min.css";

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <Router>
        <Header />
        <RouteMapper />
      </Router>
    </Provider>
  </React.StrictMode>,
  document.getElementById("root")
);
