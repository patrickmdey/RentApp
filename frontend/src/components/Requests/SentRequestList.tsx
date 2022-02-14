import {skipToken} from '@reduxjs/toolkit/dist/query';
import {useState} from 'react';
import {Card, Tabs, Tab} from 'react-bootstrap';
import {useListRentProposals} from '../../api/rentProposals/rentProposalsSlice';
import useUserId from '../../hooks/useUserId';
import {strings} from '../../i18n/i18n';
import {SENT_STRING, states} from '../../views/Requests';
import NoDataCard from '../NoDataCard';
import RequestCardList from './RequestCardList';
import usePaginatedResponse from '../../hooks/usePaginatedResponse';
import PagesList from '../PagesList';
import {RentProposal} from '../../api/rentProposals/types';
import LoadingComponent from '../LoadingComponent';
import Error from '../Error';

function SentRequestList() {
    const id = useUserId();

    const [key, setKey] = useState('pending');

    const [pendingPage, setPendingPage] = useState(1);
    const [acceptedPage, setAcceptedPage] = useState(1);
    const [declinedPage, setDeclinedPage] = useState(1);

    const {
        data: pendingS,
        isSuccess: pendingSSucc,
        pages: pendingPages,
        isLoading: pSLoad,
        error: pendingSError
    } = usePaginatedResponse(
        useListRentProposals(
            id !== null
                ? {
                    userId: id,
                    type: SENT_STRING,
                    state: states.pending,
                    page: pendingPage
                }
                : skipToken
        )
    );

    const {
        data: acceptedS,
        isSuccess: acceptedSSucc,
        pages: acceptedPages,
        isLoading: aSLoad,
        error: acceptedSError
    } = usePaginatedResponse(
        useListRentProposals(
            id !== null
                ? {
                    userId: id,
                    type: SENT_STRING,
                    state: states.accepted,
                    page: acceptedPage
                }
                : skipToken
        )
    );

    const {
        data: declinedS,
        isSuccess: declinedSSucc,
        pages: declinedPages,
        isLoading: dSLoad,
        error: declinedSError
    } = usePaginatedResponse(
        useListRentProposals(
            id !== null
                ? {
                    userId: id,
                    type: SENT_STRING,
                    state: states.declined,
                    page: declinedPage
                }
                : skipToken
        )
    );

    const anyError = pendingSError || acceptedSError || declinedSError;
    if (anyError && 'status' in anyError)
        return (
            <Error
                error={anyError.status}
                message={typeof anyError.data === 'string' ? anyError.data : undefined}
            />
        );

    return (
        <Card className='card-style min-height' color='red'>
            <Tabs activeKey={key} onSelect={(k) => k != null && setKey(k)}>
                <Tab eventKey='pending' title={strings.collection.requests.pendingTitle}>
                    {pSLoad ? (
                        <LoadingComponent/>
                    ) : pendingSSucc && pendingS && pendingS.length > 0 ? (
                        <>
                            <RequestCardList isSent={true} requests={pendingS}/>
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
                    {aSLoad ? (
                        <LoadingComponent/>
                    ) : acceptedSSucc && acceptedS && acceptedS.length > 0 ? (
                        <>
                            <RequestCardList isSent={true} requests={acceptedS}/>
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
                    {dSLoad ? (
                        <LoadingComponent/>
                    ) : declinedSSucc && declinedS && declinedS.length > 0 ? (
                        <>
                            <RequestCardList isSent={true} requests={declinedS}/>
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

export default SentRequestList;
