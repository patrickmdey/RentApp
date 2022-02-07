import {Card, Container, Nav, Tab, Tabs} from "react-bootstrap";
import {useState} from "react";
import {strings} from "../i18n/i18n";
import {useListRentProposals} from "../features/api/rentProposals/rentProposalsSlice";
import RequestCardList from "../components/Requests/RequestCardList";
import useUserId from "../hooks/useUserId";
import {skipToken} from "@reduxjs/toolkit/dist/query";

export const RECEIVED_STRING = "received";
export const SENT_STRING = "sent";

export enum states {
    pending = 0,
    accepted,
    declined,
}

export default function Requests() {
    const [requestsReceived, setRequestReceived] = useState(1);
    const [requestsSent, setRequestSent] = useState(1);

    const id = useUserId();

    const [key, setKey] = useState("pending");

    const {data: pendingR, isSuccess: pendingRSucc} = useListRentProposals(id !== null ? {
        userId: id,
        type: RECEIVED_STRING,
        state: states.pending,
    } : skipToken);

    const {data: acceptedR, isSuccess: acceptedRSucc} = useListRentProposals(id !== null ? {
        userId: id,
        type: RECEIVED_STRING,
        state: states.accepted,
    } : skipToken);

    const {data: declinedR, isSuccess: declinedRSucc} = useListRentProposals(id !== null ? {
        userId: id,
        type: RECEIVED_STRING,
        state: states.declined,
    } : skipToken);

    function onSelectReceived() {
    }

    function onSelectSent() {
    }

    return (
        <Container className="min-height">
            <div className="row align-items-start justify-content-center mb-2 g-2">
                <div className="col-md-3 col-lg-3 col-12 ">
                    <Card className="card-style ">
                        <Card.Body>
                            <h4>{strings.collection.requests.title}</h4>
                            <hr/>
                            <Tab.Container id="left-tabs-example" defaultActiveKey="first">
                                <Nav variant="pills" className="flex-column">
                                    <Nav.Item>
                                        <Nav.Link
                                            className="request-pill w-100 text-start"
                                            eventKey="first"
                                            onClick={onSelectReceived}
                                        >
                                            <p className="my-1">
                                                {strings.collection.requests.sentTitle}
                                            </p>
                                        </Nav.Link>
                                    </Nav.Item>
                                    <Nav.Item>
                                        <Nav.Link
                                            eventKey="second"
                                            className="request-pill w-100 text-start"
                                            onClick={onSelectSent}
                                        >
                                            <p className="my-1">
                                                {strings.collection.requests.receivedTitle}
                                            </p>
                                        </Nav.Link>
                                    </Nav.Item>
                                </Nav>
                            </Tab.Container>
                        </Card.Body>
                    </Card>
                </div>
                <div className="col-md-9 col-lg-9 col-12 ">
                    <Card className="card-style min-height">
                        <Tabs activeKey={key} onSelect={(k) => k != null && setKey(k)}>
                            <Tab
                                eventKey="pending"
                                title={strings.collection.requests.sentTitle}
                            >
                                {pendingRSucc && pendingR && <RequestCardList {...pendingR} />}
                            </Tab>
                            <Tab
                                eventKey="accepted"
                                title={strings.collection.requests.acceptedTitle}
                            >
                                {acceptedRSucc && acceptedR && (
                                    <RequestCardList {...acceptedR} />
                                )}
                            </Tab>
                            <Tab
                                eventKey="declined"
                                title={strings.collection.requests.declinedTitle}
                            >
                                {declinedRSucc && declinedR && (
                                    <RequestCardList {...declinedR} />
                                )}
                            </Tab>
                        </Tabs>
                    </Card>
                </div>
            </div>
        </Container>
    );
}
