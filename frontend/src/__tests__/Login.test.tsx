/**
 * @jest-environment jsdom
 */

import React from "react";
import { render, fireEvent } from "@testing-library/react";
import Login from "../pages/Login";
import LogInComponent from "../components/LogIn";

function RenderComponent(){
    return render(<LogInComponent/>)
}

describe("<LogInComponent/>", () =>{
    test("should display somehting", async () => {

    })
})