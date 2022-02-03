import {Card} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {Article} from "../features/api/articles/types";
import {useListImages} from "../features/api/images/imagesSlice";
import {useFindLocation} from "../features/api/locations/locationsSlice";
import {useFindUser} from "../features/api/users/usersSlice";
import Rating from "./Rating";
import {skipToken} from "@reduxjs/toolkit/dist/query";
import {GeoAltFill} from "react-bootstrap-icons";

function ArticleCard(article: Article) {
    const {
        title,
        pricePerDay,
        rating,
        imagesUrl,
        ownerUrl,
        timesReviewed,
        id
    } = article;

    const {data: owner, isSuccess: ownerIsSuccess} = useFindUser(ownerUrl);

    const {data: location} = useFindLocation(
        ownerIsSuccess && owner ? owner.locationUrl : skipToken
    );

    let navigate = useNavigate();
    const goToArticle = () => {
        let path = new URL(`/articles/${id}`, process.env.REACT_APP_BASE_URL); // TODO: usar url del articulo article.url
        navigate(path);
    };

    const {data, isSuccess} = useListImages(imagesUrl);
    return (
        <Card
            onClick={goToArticle}
            className="marketplace-card-style text-dark bg-light mb-4"
        >
            {isSuccess && data && data.length && (
                <div className="marketplace-card-img-container">
                    <Card.Img
                        className="card-image"
                        variant="top"
                        src={data[0].url.toString()}
                    />
                </div>
            )}
            <Card.Title as="h4">{title}</Card.Title>
            <div>
                <Card.Subtitle className="marketplace-card-info-container">
                    <div className="display-flex">
                        <GeoAltFill size="7%" className="color-primary"></GeoAltFill>
                        {location && <p>{location.name}</p>}
                    </div>

                    <Rating rating={rating} timesReviewed={timesReviewed}></Rating>
                    <h3>${pricePerDay}</h3>
                </Card.Subtitle>
            </div>
        </Card>
    );
}

export default ArticleCard;
