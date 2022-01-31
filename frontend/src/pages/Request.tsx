import PendingRequests from "../components/Requests/PendingRequests";
import {Button, Card, Col, Container, Nav, Row, Tab, Tabs} from "react-bootstrap";
import {useState} from "react";

export default function Requests(){

    const [requestsReceived, setRequestReceived] = useState(1)
    const [requestsSent, setRequestSent] = useState(1)

    function onSelectReceived(){

    }

    function onSelectSent() {

    }

    function onSelectedRejected (){

    }

    function onSelectedAccepted(){

    }
    function onSelectedPending() {

    }

    return(
        <Container className="min-height">
            <div className="row align-items-start justify-content-center mb-2 g-2">

                <div className="col-md-3 col-lg-3 col-12 ">
            <Card  className="card-style " >
                <Card.Body>
                <h4>
                    REquertete
                </h4>
                <hr/>
                    <Tab.Container id="left-tabs-example" defaultActiveKey="first">
                                <Nav variant="pills" className="flex-column">
                                    <Nav.Item>
                                        <Nav.Link className="request-pill w-100 text-start" eventKey="first" onClick={onSelectReceived}>
                                           <p className="my-1">
                                               Tab 1
                                           </p>
                                        </Nav.Link>
                                    </Nav.Item>
                                    <Nav.Item>
                                        <Nav.Link eventKey="second" className="request-pill w-100 text-start" onClick={onSelectSent}>
                                            <p className="my-1">
                                                Tab 2
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


                <Row className="cols-3 g-1 justify-content-between mb-1">
                    <Col>
                        <button className="w-100 btn bg-color-secondary btn-dark  color-grey"
                                onClick={onSelectedPending}
                        >
                            AAAAAAAA
                        </button>
                    </Col>
                    <Col>
                        <button className="w-100 btn bg-color-action color-grey"
                                onClick={onSelectedAccepted}

                        >
                            AAAAAAAA
                        </button>
                    </Col>
                    <Col>
                        <button className="w-100 btn bg-color-action color-grey"
                                onClick={onSelectedRejected}
                        >
                            AAAAAAAA
                        </button>
                    </Col>
                </Row>




            </Card>
                </div>
            </div>
        </Container>
            
    )
}