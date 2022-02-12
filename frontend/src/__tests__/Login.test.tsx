/**
 * @jest-environment jsdom
 */

import React from "react";
import {render, fireEvent, screen, waitFor} from "@testing-library/react";
import { MemoryRouter} from 'react-router-dom'
import LogInComponent from "../components/LogIn";
import {Provider} from "react-redux";
import {persistor, store} from "../store";
import {PersistGate} from "redux-persist/lib/integration/react";

function RenderComponent(){
    return render(<Provider store={store}>
        <MemoryRouter>
            <PersistGate loading={null} persistor={persistor}>
                <LogInComponent/>
            </PersistGate>
        </MemoryRouter>
    </Provider>)
}

describe("Login Component", () =>{
    beforeEach(RenderComponent);

    it("should display required error when all fields are empty", async () => {
        fireEvent.submit(screen.getAllByRole("button")[0]);

        expect(await screen.findAllByRole("note")).toHaveLength(2);
    });

    it("should not display error when all fields is valid", async () => {

        fireEvent.input(screen.getByRole("textbox"), {
            target: {
                value: "test@mail.com"
            }
        });

        fireEvent.input(screen.getByRole("textbox"), {
            target: {
                value: "password123"
            }
        });

        fireEvent.submit(screen.getAllByRole("button")[0]);

        await waitFor(() => expect(screen.queryAllByRole("note")).toHaveLength(0));
    });
})