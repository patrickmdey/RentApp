import {Button, Card, Col, Image, Row} from 'react-bootstrap';
import {LinkContainer} from 'react-router-bootstrap';
import {strings} from '../../i18n/i18n';
import {useAppSelector} from '../../hooks';
import useUserId from '../../hooks/useUserId';
import {useFindUser} from '../../api/users/usersSlice';

const logo = require('../../assets/images/rentapp-favicon.png');

export default function LandingTitle() {
    // const isAuthenticated = useAppSelector((state) => state.auth.token != null);
    const userId = useUserId();
    const isAuthenticated = userId != null;

    // TODO: Redirect to where the user is stored
    //const firstName = useAppSelector((state) => 'Lucas');

    // TODO: Redirect this to where requests are handled
    interface RequestsData {
        pending: Number;
        accepted: Number;
        rejected: Number;
    }

    function LoggedUserCard() {
        const {data: user, isLoading, isError, isSuccess, error} = useFindUser('users/' + userId);

        const requests: RequestsData = {
            pending: user?.pendingRequestAmount || 0,
            accepted: user?.acceptedRequestAmount || 0,
            rejected: user?.declinedRequestAmount || 0
        };

        return (
            <Col md={6} lg={6} className='d-flex flex-column justify-content-center align-items-center'>
                <h1>{strings.formatString(strings.collection.landing.titleLoggedIn, user?.firstName || '')}</h1>
                {requests.pending === 0 ? (
                    <p className='lead'>{strings.collection.landing.textNoRequests}</p>
                ) : (
                    <div>
                        <p className='lead'>
                            {strings.formatString(
                                strings.collection.landing.pendingRequests,
                                requests.pending.toString()
                            )}
                        </p>
                        <p className='lead'>
                            {strings.formatString(
                                strings.collection.landing.acceptedRequests,
                                requests.accepted.toString()
                            )}
                        </p>
                        <p className='lead'>
                            {strings.formatString(
                                strings.collection.landing.rejectedRequests,
                                requests.rejected.toString()
                            )}
                        </p>
                    </div>
                )}
            </Col>
        );
    }

    function NotLoggedUserCard() {
        return (
            <Col md={6} lg={6}>
                <h1>{strings.collection.landing.title}</h1>
                <p className='lead'>{strings.collection.landing.explanation}</p>
                <LinkContainer to='/marketplace'>
                    <Button className='text-center w-100 bg-color-action color-grey'>
                        {strings.collection.landing.button}
                    </Button>
                </LinkContainer>
            </Col>
        );
    }

    return (
        <Row className="landing-margins landing-title-container">
            {isAuthenticated ? <LoggedUserCard/> : <NotLoggedUserCard/>}
            <Col md={6} lg={6} className='d-flex justify-content-center align-items-center'>
                <Image
                    src='https://thumbs.dreamstime.com/b/happy-man-mobile-shopping-choose-product-goods-smartphone-give-rating-feedback-vector-173000676.jpg'
                    width='150px'
                    height='auto'
                    roundedCircle
                />
                <Image
                    src='https://img.freepik.com/vector-gratis/ilustracion-concepto-escoger_114360-553.jpg?size=338&ext=jpg&ga=GA1.2.1931844515.1631145600'
                    width='150px'
                    height='auto'
                    roundedCircle
                />
                <Image src={logo} width='150px' height='auto' roundedCircle/>
            </Col>
        </Row>
    );
}
