import {Button, Card, Form, InputGroup} from "react-bootstrap";
import {strings} from "../i18n/i18n";
import {Controller, Path, useForm} from "react-hook-form";
import {BsFilterLeft, BsPinMap} from "react-icons/bs";

interface FilterCardForm {
    name: string,
    category: string,
    orderBy: string,
    location: string,
    maxPrice: string,
    minPrice: string
}


interface FilterCardProps {
    onUpdate: (data: FilterCardForm) => void
}

function FilterCard(props: FilterCardProps) {
    const {control, handleSubmit} = useForm<FilterCardForm>({
        defaultValues: {name: "", maxPrice: "", minPrice: ""}
    })

    function TextBox(label: string, name: Path<FilterCardForm>, type: string, prependIcon: any = null, appendIcon: any = null) {
        return (

            <Controller name={name} control={control}
                        render={({field}) =>
                            <Form.Group className="mt-3 ">
                                <Form.Label className="font-weight-bold"> <b>{label}</b> </Form.Label>
                                <InputGroup>
                                    {prependIcon != null && <InputGroup.Text>{prependIcon}</InputGroup.Text>}
                                    <Form.Control type={type} {...field} />
                                    {appendIcon != null && <InputGroup.Text>{appendIcon}</InputGroup.Text>}
                                </InputGroup>
                            </Form.Group>
                        }/>
        )
    }

    function Select(label: string, name: Path<FilterCardForm>, options: [string, string][], prependIcon: any = null, appendIcon: any = null) {
        return (

            <Controller defaultValue="" name={name} control={control} render={({field}) =>
                <Form.Group className="mt-3">
                    <Form.Label className="font-weight-bold "> <b>{label}</b> </Form.Label>
                    <InputGroup>
                        {prependIcon != null && <InputGroup.Text>{prependIcon}</InputGroup.Text>}

                        <Form.Select {...field}>
                            {options.map(t => <option key={t[0]} value={t[0]} label={t[1]}/>)}
                        </Form.Select>
                        {appendIcon != null && <InputGroup.Text>{appendIcon}</InputGroup.Text>}

                    </InputGroup>

                </Form.Group>
            }/>

        )
    }

    return (
        <Card className="card-style filters-card col-md-3 col-lg-3 col-12">
            <Card.Header className=" d-flex align-items-center ">
                <h4 className="color-rentapp-black col-9">{strings.collection.filter.title}</h4>
            </Card.Header>
            <Form onSubmit={handleSubmit(props.onUpdate)}>
                <Card.Body style={{padding: "0px"}}>


                    {TextBox(strings.collection.filter.name, "name", "text")}
                    {Select(strings.collection.filter.category, "category", [
                        ["-1", strings.collection.filter.everyCategory],
                        ["1", "One"],
                        ["2", "Two"],
                        ["3", "Three"],
                    ])}
                    {Select(strings.collection.filter.orderBy, "orderBy", [
                        ["-1", "A-Z"],
                        ["1", "One"],
                        ["2", "Two"],
                        ["3", "Three"],
                    ], null, <BsFilterLeft/>)}

                    {Select(strings.collection.filter.location, "location", [
                        ["-1", strings.collection.filter.everyLocation],
                        ["1", "One"],
                        ["2", "Two"],
                        ["3", "Three"],
                    ], null, <BsPinMap/>)}

                    {TextBox(strings.collection.filter.minPrice, "minPrice", "number", "$")}

                    {TextBox(strings.collection.filter.maxPrice, "maxPrice", "number", "$")}


                </Card.Body>
                <Button type="submit" className="w-100 mt-3">
                    {strings.collection.filter.button}
                </Button>
            </Form>
        </Card>
    )
}

export default FilterCard;
