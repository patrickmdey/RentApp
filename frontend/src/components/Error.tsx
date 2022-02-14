import errorImage from '../assets/images/error.png';
import {strings} from "../i18n/i18n";

function Error(props: {
    error: number | 'CUSTOM_ERROR' | 'FETCH_ERROR' | 'PARSING_ERROR';
    message: string | undefined;
}) {
    const {error, message} = props;
    return (
        <div className='d-flex flex-column justify-content-center align-items-center'>
            <img src={errorImage}/>
            <h3 className='fw-bold'>Error {error}</h3>
            <p className='lead'>{message}</p>
            <a href="/">{strings.collection.errors.pageNotFoundSubtitle}</a>
        </div>
    );
}

export default Error;
