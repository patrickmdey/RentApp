import React from 'react';
import ReactDOM from 'react-dom';
import './css/index.css';
import { Provider } from 'react-redux';
import { persistor, store } from './store';
import 'typeface-roboto';
import './scss/app.scss';
import { PersistGate } from 'redux-persist/integration/react';
import Startup from './startup';

ReactDOM.render(
	<div className='bg-color-grey min-height'>
		<React.StrictMode>
			<Provider store={store}>
				<PersistGate loading={null} persistor={persistor}>
					<Startup />
				</PersistGate>
			</Provider>
		</React.StrictMode>
	</div>,
	document.getElementById('root')
);
