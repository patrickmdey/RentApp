import {Badge, Card, Container, Nav, Tab, Tabs} from 'react-bootstrap';
import {strings} from '../i18n/i18n';
import SentRequestList from '../components/Requests/SentRequestList';
import ReceivedRequestList from '../components/Requests/ReceivedRequestList';
import useUserId from '../hooks/useUserId';
import {useFindUser} from '../api/users/usersSlice';
import {useState, useEffect} from 'react';

export const RECEIVED_STRING = 'received';
export const SENT_STRING = 'sent';

export const states = {
    pending: 'PENDING',
    accepted: 'ACCEPTED',
    declined: 'DECLINED'
};

export default function Requests() {
    const userId = useUserId();

    const {data: user} = useFindUser(`users/${userId}`);

    const [receivedAmount, setReceievedAmount] = useState(0);

    useEffect(() => setReceievedAmount(user && user.owner ? user.pendingRequestAmount : 0), [user]);

    return (
        <Container className='min-height'>
            <Tab.Container id='left-tabs-example' defaultActiveKey='first'>
                <div className='row align-items-start justify-content-center mb-2 g-2'>
                    <div className='col-md-3 col-lg-3 col-12 '>
                        <Card className='card-style '>
                            <Card.Body>
                                <h4>{strings.collection.requests.title}</h4>
                                <hr/>
                                <Nav variant='pills' className='flex-column'>
                                    <Nav.Item>
                                        <Nav.Link className='request-pill w-100 text-start mb-1'
                                                  eventKey='first'>
                                            <p className='my-1'>{strings.collection.requests.sentTitle}</p>
                                        </Nav.Link>
                                    </Nav.Item>
                                    <Nav.Item>
                                        <Nav.Link eventKey='second'
                                                  className='request-pill w-100 text-start'>
                                            <div className='d-flex'>
                                                <p className='my-1'>{strings.collection.requests.receivedTitle}</p>
                                                {receivedAmount > 0 && (
                                                    <p className='my-1'>
                                                        <Badge className='bg-rentapp-red ms-1'>{receivedAmount}</Badge>
                                                    </p>
                                                )}
                                            </div>
                                        </Nav.Link>
                                    </Nav.Item>
                                </Nav>
                            </Card.Body>
                        </Card>
                    </div>
                    <div className='col-md-9 col-lg-9 col-12 '>
                        <Tab.Content>
                            <Tab.Pane eventKey='first'>
                                <SentRequestList/>
                            </Tab.Pane>
                            <Tab.Pane eventKey='second'>
                                <ReceivedRequestList/>
                            </Tab.Pane>
                        </Tab.Content>
                    </div>
                </div>
            </Tab.Container>
        </Container>
    );
}
