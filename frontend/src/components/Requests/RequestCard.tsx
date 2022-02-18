import {RentProposal} from '../../api/rentProposals/types';
import {Button, Card, Image, Modal, Row} from 'react-bootstrap';
import {strings} from '../../i18n/i18n';
import {useFindArticle} from '../../api/articles/articlesSlice';
import {useFindUser} from '../../api/users/usersSlice';
import {states} from '../../views/Requests';
import {useState} from 'react';
import {CircleFill} from 'react-bootstrap-icons';
import {useUpdateRentProposal} from '../../api/rentProposals/rentProposalsSlice';
import {useNavigate} from 'react-router-dom';
import {useListImages} from '../../api/images/imagesSlice';
import {skipToken} from '@reduxjs/toolkit/dist/query';
import LoadingComponent from '../LoadingComponent';

export default function RequestCard(props: { request: RentProposal; isSent: boolean }) {
    const {message, startDate, endDate, marked, state, url, articleUrl, renterUrl} = props.request;

    const {data: article, isSuccess: articleSuccess} = useFindArticle(articleUrl);

    const {data: renter, isSuccess: renterSuccess} = useFindUser(renterUrl);

    const {data: images, isSuccess: imagesSuccess} = useListImages(
        articleSuccess && article ? article.imagesUrl : skipToken
    );

    const [show, setShow] = useState(false);

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [proposalState, setProposalState] = useState(state.toString());

    const [declineProposal, declineResult] = useUpdateRentProposal();
    const [acceptProposal, acceptResult] = useUpdateRentProposal();

    const handleDecline = () => {
        declineProposal({state: states.declined, url: url});
        setProposalState(states.declined);
        handleClose();
    };

    const handleAccept = () => {
        acceptProposal({state: states.accepted, url: url});
        setProposalState(states.accepted);
    };

    let navigate = useNavigate();
    const goToArticle = (articleId: number) => {
        navigate(`/articles/${articleId}`);
    };
    return (
        <>
            {articleSuccess && article && renterSuccess && renter && (
                <>
                    <Card className='card-style my-requests-card my-2'>
                        <div className='d-flex align-items-center'>
                            {marked && !props.isSent && <CircleFill className='color-rentapp-red me-3'/>}

                            <h3
                                onClick={() => {
                                    goToArticle(article.id);
                                }}
                                className='request-card-title mb-0'
                            >
                                {article.title}
                            </h3>
                        </div>
                        <hr/>
                        <Row>
                            <div className='col-3'>
                                {imagesSuccess && images && images[0] && (
                                    <Image src={images[0].url.toString()} width='100%'/>
                                )}
                            </div>
                            <div className='col-9'>
                                <p className='lead mb-2'>
                                    {strings.formatString(
                                        strings.collection.requestCard.name,
                                        renter.firstName,
                                        renter.lastName
                                    )}
                                </p>
                                <Row className='justify-content-start'>
                                    <p className='lead col-lg-6 col-md-6 col-12'>
                                        {strings.formatString(strings.collection.requestCard.startDate, startDate)}
                                    </p>
                                    <p className='lead col-lg-6 col-md-6 col-12'>
                                        {strings.formatString(strings.collection.requestCard.endDate, endDate)}
                                    </p>
                                </Row>
                                <h4> {strings.collection.requestCard.messageTitle}</h4>
                                <p> {message} </p>
                            </div>
                        </Row>
                        {acceptResult.isLoading || declineResult.isLoading ? (
                            <div className='d-flex justify-content-end my-2'>
                                <LoadingComponent/>
                            </div>
                        ) : (
                            proposalState === states.pending &&
                            !props.isSent && (
                                <div className='d-flex justify-content-end my-2'>
                                    <button onClick={handleShow} className='btn btn-link color-danger'>
                                        {strings.collection.requestCard.rejectButton}
                                    </button>
                                    <Button onClick={handleAccept} className='bg-color-action color-grey me-1'>
                                        {strings.collection.requestCard.acceptButton}
                                    </Button>
                                </div>
                            )
                        )}
                    </Card>

                    {!props.isSent && (
                        <Modal show={show} onHide={handleClose}>
                            <Modal.Header className='bg-color-grey' closeButton>
                                <Modal.Title as='h3'>{strings.collection.requestCard.rejectTitle}</Modal.Title>
                            </Modal.Header>
                            <Modal.Body className='bg-color-grey d-flex align-items-center'>
                                <p className='lead'>{strings.collection.requestCard.rejectText}</p>
                            </Modal.Body>
                            <Modal.Footer className='bg-color-grey'>
                                <Button onClick={handleDecline} className='bg-color-danger'>
                                    {strings.collection.requestCard.rejectButton}
                                </Button>
                            </Modal.Footer>
                        </Modal>
                    )}
                </>
            )}
        </>
    );
}
