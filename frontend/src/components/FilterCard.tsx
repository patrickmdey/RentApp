import {Button, Card, Form} from "react-bootstrap";
import {strings} from "../i18n/i18n";
import {useState} from "react";

function FilterCard() {
    const [state, setState] = useState({})
    return (
        <Card className="card-style filters-card col-md-3 col-lg-3 col-12">
            <Card.Header className=" d-flex align-items-center ">
                <h4 className="color-rentapp-black col-9">{strings.collection.filter.title}</h4>
            </Card.Header>
            <Card.Body>
                <Form.Group>
                    <Form.Label className="font-weight-bold"> {strings.collection.filter.name} </Form.Label>
                    <Form.Control type="text"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label className="font-weight-bold mt-3"> {strings.collection.filter.category} </Form.Label>
                    <Form.Select>
                        <option>{strings.collection.filter.everyCategory}</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                        <option value="3">Three</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group>
                    <Form.Label className="font-weight-bold mt-3"> {strings.collection.filter.category} </Form.Label>
                    <Form.Select>
                        <option>A-Z</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                        <option value="3">Three</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group>
                    <Form.Label className="font-weight-bold mt-3"> {strings.collection.filter.location} </Form.Label>
                    <Form.Select>
                        <option>{strings.collection.filter.everyLocation}</option>
                        <option value="1">One</option>
                        <option value="2">Two</option>
                        <option value="3">Three</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group>
                    <Form.Label> {strings.collection.filter.minPrice} </Form.Label>
                    <Form.Control type="text"/>
                </Form.Group>
                <Form.Group>
                    <Form.Label> {strings.collection.filter.maxPrice} </Form.Label>
                    <Form.Control type="text"/>
                </Form.Group>
            </Card.Body>

            <Button>
                {strings.collection.filter.button}
            </Button>
        </Card>

    );
}

export default FilterCard;
