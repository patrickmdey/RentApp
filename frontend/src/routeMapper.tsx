import {Route, Routes} from "react-router-dom";
import Home from "./pages/home";
import Users from "./pages/users";
import About from "./pages/about";


// function RouteMapper(props: ReduxProps) {
export default function RouteMapper() {

    return (
        <div>
            <Routes>
                <Route path="/about" element={<About/>}/>
                <Route path="/users" element={<Users/>}/>
                <Route path="/" element={<Home/>}/>
            </Routes>
        </div>
    );
}

// const mapStateToProps = (state: RootState) => ({
//     lang: state.i18n.lang
// })
//
// const connector = connect(mapStateToProps);
// type ReduxProps = ConnectedProps<typeof connector>;
//
// export default connector(RouteMapper);

