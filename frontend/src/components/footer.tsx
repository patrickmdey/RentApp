import {Button, Card, Image, ListGroup, ListGroupItem} from "react-bootstrap";
import {strings} from "../i18n/i18n";
import React from "react";
import {useAppDispatch} from "../hooks";
import {setLanguage} from "../features/i18n/i18nSlice"

const logo = require("../images/rentapp-favicon.png");


// function Footer(props: ReduxProps) {
export default function Footer() {
    const dispatcher = useAppDispatch();

    function updateLanguage(language: string) {
        dispatcher(setLanguage(language));
    }

    return (
        <footer className="mt-auto footer-style bg-color-primary">
            <Card.Body className="row my-n4">
                <div className="col-4 footer-image-col">
                    <Image src={logo} className="mt-n3" height="180px" width="auto" alt="RentApp"/>
                </div>
                <div className="col-4">
                    <ListGroup as="ul" className="col-6 list-unstyled">
                        <h3 className="h3 fw-bold color-grey">
                            {strings.collection.footer.team}
                        </h3>
                        <ListGroupItem bsPrefix="color-grey">
                            Santos Rosati
                        </ListGroupItem>
                        <ListGroupItem bsPrefix="color-grey">
                            Matias Lombardi
                        </ListGroupItem>
                        <ListGroupItem bsPrefix="color-grey">
                            Patrick Dey
                        </ListGroupItem>
                        <ListGroupItem bsPrefix="color-grey">
                            Lucas Dell'Isola
                        </ListGroupItem>
                    </ListGroup>
                </div>

                <div className="col-4">
                    <ListGroup className="list-unstyled" as="ul">
                        <ListGroupItem bsPrefix="h3 fw-bold color-grey">
                            {strings.collection.footer.contact}
                        </ListGroupItem>
                        <ListGroupItem bsPrefix="lead color-grey">
                            {strings.collection.footer.email}:
                            <a href="mailto:paw2021b3@gmail.com" className="color-secondary">paw2021b3@gmail.com</a>
                        </ListGroupItem>
                        <ListGroupItem bsPrefix="h3 fw-bold color-grey">
                            {strings.collection.footer.language}
                        </ListGroupItem>
                        <ListGroupItem bsPrefix=" ">

                            <Button className="me-1 color-secondary" onClick={() => updateLanguage('en')}>
                                {strings.collection.footer.english}
                            </Button>
                            <Button className="color-secondary" onClick={() => updateLanguage('es')}>
                                {strings.collection.footer.spanish}
                            </Button>


                        </ListGroupItem>
                    </ListGroup>
                </div>

            </Card.Body>
        </footer>
    )
}

// const mapStateToProps = (state: RootState) => ({
//     lang: state.i18n.lang
// })
//
// const connector = connect(mapStateToProps);
// type ReduxProps = ConnectedProps<typeof connector>;
//
// export default connector(Footer);


