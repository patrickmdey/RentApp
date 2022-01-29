import {Button, Col, Image, Row} from "react-bootstrap";
import {LinkContainer} from "react-router-bootstrap";
import {strings} from "../i18n/i18n";

const logo = require("../images/rentapp-favicon.png");

export default function LandingTitle(){
    return (
        <Row className=" landing-title-container">
            <Col md="6">
                <h1>
                    {strings.collection.landing.title}
                </h1>
                <p className="lead">
                    {strings.collection.landing.explanation}
                </p>
                <LinkContainer to="/marketplace">
                <Button className="text-center w-100 bg-color-action color-grey" >
                    {strings.collection.landing.button}
                </Button>
                </LinkContainer>
            </Col>
            <Col md="6" className="d-flex justify-content-center align-items-center">
                    <Image src="https://thumbs.dreamstime.com/b/happy-man-mobile-shopping-choose-product-goods-smartphone-give-rating-feedback-vector-173000676.jpg"
                        width="150px" height="auto" roundedCircle
                    />
                    <Image src="https://img.freepik.com/vector-gratis/ilustracion-concepto-escoger_114360-553.jpg?size=338&ext=jpg&ga=GA1.2.1931844515.1631145600"
                           width="150px" height="auto" roundedCircle
                    />
                    <Image src={logo} width="150px" height="auto" roundedCircle />
            </Col>
        </Row>
    )
}