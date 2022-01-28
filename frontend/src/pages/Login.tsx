import LogInComponent from "../components/LogIn"
import {Container} from "react-bootstrap";

export default function Login() {
    return (
        <Container className="main-container min-height">
            <LogInComponent/>
        </Container>
    )
}