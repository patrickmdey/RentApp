import { Navigate, Route, Routes, useLocation } from 'react-router-dom';
import Marketplace from './pages/Marketplace';
import Article from './pages/Article';
import Login from './pages/Login';
import Landing from './pages/Landing';
import Register from './pages/Register';
import CreateArticle from './pages/CreateArticle';
import Requests from './pages/Request';
import Review from './pages/Review';
import Profile from './pages/Profile';
import useUserId from './hooks/useUserId';
import EditReview from './pages/EditReview';
import EditArticle from './pages/EditArticle';

function RequireAuth(props: { children: JSX.Element }) {
	const id = useUserId();
	const location = useLocation();
	return id == null ? <Navigate to='/login' replace state={{ path: location.pathname }} /> : props.children;
}

export default function RouteMapper() {
	return (
		<div>
			<Routes>
				<Route path='/login' element={<Login />} />
				<Route path='/register' element={<Register />} />
				<Route
					path='/profile'
					element={
						<RequireAuth>
							<Profile />
						</RequireAuth>
					}
				/>
				<Route
					path='/proposals'
					element={
						<RequireAuth>
							<Requests />
						</RequireAuth>
					}
				/>

				<Route path='/marketplace' element={<Marketplace />} />
				<Route path='/' element={<Landing />} />

				<Route
					path='/createArticle'
					element={
						<RequireAuth>
							<CreateArticle />
						</RequireAuth>
					}
				/>
				<Route path={`/articles/:id`} element={<Article />} />
				<Route
					path='/articles/:id/edit'
					element={
						<RequireAuth>
							<EditArticle />
						</RequireAuth>
					}
				/>
				<Route
					path='/createReview'
					element={
						<RequireAuth>
							<Review />
						</RequireAuth>
					}
				/>
				<Route
					path={`/editReview/:id`}
					element={
						<RequireAuth>
							<EditReview />
						</RequireAuth>
					}
				/>
			</Routes>
		</div>
	);
}
