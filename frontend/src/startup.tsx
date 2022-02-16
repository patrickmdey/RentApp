import { useEffect, useReducer } from 'react';
import RouteMapper from './views/routeMapper';
import { BrowserRouter as Router } from 'react-router-dom';
import { strings } from './i18n/i18n';
import { useAppSelector } from './hooks';
import Footer from './components/Footer';
import Header from './components/Header';

export default function Startup() {
	const lang = useAppSelector((state) => state.i18n.lang);
	const token = useAppSelector((state) => state.auth.token);

	const [_, forceUpdate] = useReducer((x) => x + 1, 0);

	useEffect(() => {
		strings.setLanguage(lang);
		forceUpdate();
	}, [lang]);

	useEffect(() => {
		strings.setLanguage(lang);
		forceUpdate();
	}, [token]);

	return (
		<div>
			<Router basename={process.env.PUBLIC_URL}>
				<Header />
				<div className='min-height'>
					<RouteMapper />
				</div>
				<Footer />
			</Router>
		</div>
	);
}
