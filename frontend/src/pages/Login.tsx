import LogInComponent from "../components/LogIn"
import {Container} from "react-bootstrap";

export default function Login() {
    return (
        <Container style={{width:"50%"}} className=" min-height">
            <LogInComponent  />
        </Container>
    )
}