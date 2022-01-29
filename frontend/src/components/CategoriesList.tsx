import {useListCategories} from "../features/api/categories/categoriesSlice";
import CategoryCard from "./CategoryCard";
import {Col, Row} from "react-bootstrap";
import {strings} from "../i18n/i18n";


export default function CategoriesList(){
    // const {data = [] , isFetching  } = useListCategories();

    const data = [
        {
        url: new URL("http://pawserver.it.itba.edu.ar/paw-2021b-3/image/59"),
        description: "Kitchen",
        imageUrl: new URL("http://pawserver.it.itba.edu.ar/paw-2021b-3/image/59")
        },
        {
        url: new URL("http://pawserver.it.itba.edu.ar/paw-2021b-3/image/59"),
        description: "Kitchen",
        imageUrl: new URL("http://pawserver.it.itba.edu.ar/paw-2021b-3/image/59")
        },
    ]

    return (
        <div className="bg-color-secondary w-100 " style={{paddingTop: '10px'}}>
            <h3 className="text-center">
                {strings.collection.categories.searchByCategories}
            </h3>
        <Row className="landing-category-container ">
            {
                data.map((cat) =>
                    <Col key={cat.url.toString()} style={{maxWidth:'15%'}}>
                        <CategoryCard  {...cat}/>
                    </Col>
                )
            }

        </Row>
        </div>
    )
}