import {skipToken} from '@reduxjs/toolkit/dist/query';
import {useState} from 'react';
import {Card, Tabs, Tab, Badge} from 'react-bootstrap';
import {useListRentProposals} from '../../api/rentProposals/rentProposalsSlice';
import useUserId from '../../hooks/useUserId';
import {strings} from '../../i18n/i18n';
import {RECEIVED_STRING, states} from '../../views/Requests';
import NoDataCard from '../NoDataCard';
import RequestCardList from './RequestCardList';
import usePaginatedResponse from '../../hooks/usePaginatedResponse';
import PagesList from '../PagesList';
import LoadingComponent from '../LoadingComponent';
import Error from '../Error';

function ReceivedRequestList(props: { pendingAmount: number }) {
    const id = useUserId();

    const [key, setKey] = useState('pending');

    const [pendingPage, setPendingPage] = useState(1);
    const [acceptedPage, setAcceptedPage] = useState(1);
    const [declinedPage, setDeclinedPage] = useState(1);

    const {
        data: pendingR,
        isSuccess: pendingRSucc,
        pages: pendingPages,
        isLoading: pRLoad,
        error: pendingRError
    } = usePaginatedResponse(
        useListRentProposals(
            id !== null
                ? {
                    userId: id,
                    type: RECEIVED_STRING,
                    state: states.pending,
                    page: pendingPage
                }
                : skipToken
        )
    );

    const {
        data: acceptedR,
        isSuccess: acceptedRSucc,
        pages: acceptedPages,
        isLoading: aRLoad,
        error: acceptedRError
    } = usePaginatedResponse(
        useListRentProposals(
            id !== null
                ? {
                    userId: id,
                    type: RECEIVED_STRING,
                    state: states.accepted,
                    page: acceptedPage
                }
                : skipToken
        )
    );

    const {
        data: declinedR,
        isSuccess: declinedRSucc,
        pages: declinedPages,
        isLoading: dRLoad,
        error: declinedRError
    } = usePaginatedResponse(
        useListRentProposals(
            id !== null
                ? {
                    userId: id,
                    type: RECEIVED_STRING,
                    state: states.declined,
                    page: declinedPage
                }
                : skipToken
        )
    );

    const anyError = pendingRError || acceptedRError || declinedRError;
    if (anyError && 'originalStatus' in anyError)
        return (
            <Error
                error={anyError.originalStatus}
                message={anyError.data}
            />
        );

    return (
        <Card className='card-style min-height'>
            <Tabs activeKey={key} onSelect={(k) => k != null && setKey(k)}>
                <Tab eventKey='pending'
                     title={<div><p>{strings.collection.requests.pendingTitle}</p>{props.pendingAmount > 0 && (
                         <p className='my-1'>
                             <Badge className='bg-rentapp-red ms-1'>{props.pendingAmount}</Badge>
                         </p>)}
                     </div>}>
                    {pRLoad ? (
                        <LoadingComponent/>
                    ) : pendingRSucc && pendingR && pendingR.length > 0 ? (
                        <>
                            <RequestCardList isSent={false} requests={pendingR}/>
                            <PagesList pages={pendingPages} page={pendingPage} setPage={setPendingPage}/>
                        </>
                    ) : (
                        <NoDataCard
                            title={strings.collection.requests.sent.noPendingTitle}
                            subtitle={strings.collection.requests.sent.noPendingSubtitle}
                        />
                    )}
                </Tab>
                <Tab eventKey='accepted' title={strings.collection.requests.acceptedTitle}>
                    {aRLoad ? (
                        <LoadingComponent/>
                    ) : acceptedRSucc && acceptedR && acceptedR.length > 0 ? (
                        <>
                            <RequestCardList isSent={false} requests={acceptedR}/>
                            <PagesList pages={acceptedPages} page={acceptedPage} setPage={setAcceptedPage}/>
                        </>
                    ) : (
                        <NoDataCard
                            title={strings.collection.requests.sent.noAcceptedTitle}
                            subtitle={strings.collection.requests.sent.noAcceptedSubtitle}
                        />
                    )}
                </Tab>
                <Tab eventKey='declined' title={strings.collection.requests.declinedTitle}>
                    {dRLoad ? (
                        <LoadingComponent/>
                    ) : declinedRSucc && declinedR && declinedR.length > 0 ? (
                        <>
                            <RequestCardList isSent={false} requests={declinedR}/>
                            <PagesList pages={declinedPages} page={declinedPage} setPage={setDeclinedPage}/>
                        </>
                    ) : (
                        <NoDataCard
                            title={strings.collection.requests.sent.noDeclinedTitle}
                            subtitle={strings.collection.requests.sent.noDeclinedSubtitle}
                        />
                    )}
                </Tab>
            </Tabs>
        </Card>
    );
}

export default ReceivedRequestList;
