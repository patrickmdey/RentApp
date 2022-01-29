import CategoriesList from "../components/CategoriesList";
import {Container, Row} from "react-bootstrap";
import LandingTitle from "../components/LandingTitle";


export default function Landing(){
    return (
        <Container className="d-flex flex-column align-items-center">
            <div style={{width: '100%'}} className="row g-0">
                <LandingTitle/>
            </div>
            <Row>
                <h1>
                    TODO: Articles with best rating
                </h1>
            </Row>
            <Row>
                <CategoriesList/>
            </Row>
            <Row>
                <h1>
                    TODO: Most rented articles
                </h1>
            </Row>

        </Container>

    )
}