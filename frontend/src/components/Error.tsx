import errorImage from '../assets/images/error.png';
import {strings} from "../i18n/i18n";
import {useNavigate} from "react-router";

function Error(props: {
    error: number | 'CUSTOM_ERROR' | 'FETCH_ERROR' | 'PARSING_ERROR';
    message: string | undefined;
}) {
    const navigate = useNavigate();
    const {error, message} = props;
    return (
        <div className='d-flex flex-column justify-content-center align-items-center'>
            <img src={errorImage}/>
            <h3 className='fw-bold'>Error {error}</h3>
            {message && message.length < 30 && <p className='lead'>{message}</p>}
            <a onClick={() => navigate("/")}>{strings.collection.errors.pageNotFoundSubtitle}</a>
        </div>
    );
}

export default Error;
