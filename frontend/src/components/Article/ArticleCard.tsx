import {Card} from 'react-bootstrap';
import {useNavigate} from 'react-router-dom';
import {Article} from '../../api/articles/types';
import {useListImages} from '../../api/images/imagesSlice';
import {useFindLocation} from '../../api/locations/locationsSlice';
import {useFindUser} from '../../api/users/usersSlice';
import Rating from '../Review/Rating';
import {skipToken} from '@reduxjs/toolkit/dist/query';
import {GeoAltFill} from 'react-bootstrap-icons';
import {strings} from "../../i18n/i18n";

function ArticleCard(article: Article) {
    const {title, pricePerDay, rating, imagesUrl, ownerUrl, timesReviewed, id} = article;

    const {data: owner, isSuccess: ownerIsSuccess} = useFindUser(ownerUrl);

    const {data: location} = useFindLocation(ownerIsSuccess && owner ? owner.locationUrl : skipToken);

    let navigate = useNavigate();
    const goToArticle = () => {
        navigate(`/articles/${id}`);
    };

    const {data, isSuccess} = useListImages(imagesUrl);
    return (
        <Card onClick={goToArticle} className='marketplace-card-style text-dark bg-light mb-4'>
            {isSuccess && data && data.length && (
                <div className='marketplace-card-img-container'>
                    <Card.Img variant='top' src={data[0].url.toString()}/>
                </div>
            )}

            <Card.Body className='marketplace-card-info-container'>
                <h4 className='text-truncate'>{title}</h4>
                <div className='d-flex mt-1 d-inline-block'>
                    <GeoAltFill className='color-primary' size='20px'/>
                    {location && <p className='lead fw-bold color-primary'>{location.name}</p>}
                </div>

                <div>
                    <Rating rating={rating} timesReviewed={timesReviewed}/>
                </div>
                <h3>${pricePerDay} {strings.collection.article.priceText}</h3>
            </Card.Body>
        </Card>
    );
}

export default ArticleCard;
