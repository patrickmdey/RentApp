import { render } from '@testing-library/react';
import { MemoryRouter, Route } from 'react-router-dom';
import App from '../../App';
import { ConstantDataProvider, UserContextProvider } from '../../context';
import { NavBarContextProvider } from '../../context/navBarContext';

const renderFromRoute = (path, locationAndHistory = {}) => {
    render(
        <MemoryRouter initialEntries={[path]}>
            <UserContextProvider>
                <ConstantDataProvider>
                    <NavBarContextProvider>
                        <App />
                        <Route
                            path="*"
                            render={({ history, location }) => {
                                locationAndHistory.location = location;
                                locationAndHistory.history = history;
                                return null;
                            }}
                        />
                    </NavBarContextProvider>
                </ConstantDataProvider>
            </UserContextProvider>
        </MemoryRouter>
    );
};

export default renderFromRoute;