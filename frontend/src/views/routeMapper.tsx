import {Navigate, Route, Routes, useLocation} from 'react-router-dom';
import Marketplace from './Marketplace';
import Article from './Article';
import Login from './Login';
import Landing from './Landing';
import Register from './Register';
import CreateArticle from './CreateArticle';
import Requests from './Requests';
import CreateReview from './CreateReview';
import Profile from './Profile';
import useUserId from '../hooks/useUserId';
import EditReview from './EditReview';
import EditArticle from './EditArticle';
import Success from './Success';

function RequireAuth(props: { children: JSX.Element }) {
    const id = useUserId();
    const location = useLocation();
    return id === null ? <Navigate to='/login' replace state={{path: location.pathname}}/> : props.children;
}

export default function RouteMapper() {
    return (
        <div>
            <Routes>
                <Route path='/login' element={<Login/>}/>
                <Route path='/register' element={<Register/>}/>
                <Route
                    path='/profile'
                    element={
                        <RequireAuth>
                            <Profile/>
                        </RequireAuth>
                    }
                />
                <Route
                    path='/proposals'
                    element={
                        <RequireAuth>
                            <Requests/>
                        </RequireAuth>
                    }
                />

                <Route path='/marketplace' element={<Marketplace/>}/>
                <Route path='/' element={<Landing/>}/>
                <Route path='/success' element={<Success/>}/>

                <Route
                    path='/createArticle'
                    element={
                        <RequireAuth>
                            <CreateArticle/>
                        </RequireAuth>
                    }
                />
                <Route path={`/articles/:id`} element={<Article/>}/>
                <Route
                    path='/articles/:id/edit'
                    element={
                        <RequireAuth>
                            <EditArticle/>
                        </RequireAuth>
                    }
                />
                <Route
                    path='/createReview'
                    element={
                        <RequireAuth>
                            <CreateReview/>
                        </RequireAuth>
                    }
                />
                <Route
                    path={`/editReview/:id`}
                    element={
                        <RequireAuth>
                            <EditReview/>
                        </RequireAuth>
                    }
                />
            </Routes>
        </div>
    );
}
