import { skipToken } from '@reduxjs/toolkit/dist/query';
import { useState } from 'react';
import { Card, Tabs, Tab } from 'react-bootstrap';
import { useListRentProposals } from '../../features/api/rentProposals/rentProposalsSlice';
import useUserId from '../../hooks/useUserId';
import { strings } from '../../i18n/i18n';
import { SENT_STRING, states } from '../../pages/Request';
import NoDataCard from '../NoData/NoDataCard';
import RequestCardList from './RequestCardList';

function SentRequestList() {
	const id = useUserId();

	const [key, setKey] = useState('pending');

	const { data: pendingS, isSuccess: pendingSSucc } = useListRentProposals(
		id !== null
			? {
					userId: id,
					type: SENT_STRING,
					state: states.pending
			  }
			: skipToken
	);

	const { data: acceptedS, isSuccess: acceptedSSucc } = useListRentProposals(
		id !== null
			? {
					userId: id,
					type: SENT_STRING,
					state: states.accepted
			  }
			: skipToken
	);

	const { data: declinedS, isSuccess: declinedSSucc } = useListRentProposals(
		id !== null
			? {
					userId: id,
					type: SENT_STRING,
					state: states.declined
			  }
			: skipToken
	);

	return (
		<Card className='card-style min-height' color='red'>
			<Tabs activeKey={key} onSelect={(k) => k != null && setKey(k)}>
				<Tab eventKey='pending' title={strings.collection.requests.pendingTitle}>
					{pendingSSucc && pendingS && pendingS.length > 0 ? (
						<RequestCardList isSent={true} requests={pendingS} />
					) : (
						<NoDataCard
							title={strings.collection.requests.sent.noPendingTitle}
							subtitle={strings.collection.requests.sent.noPendingSubtitle}
						/>
					)}
				</Tab>
				<Tab eventKey='accepted' title={strings.collection.requests.acceptedTitle}>
					{acceptedSSucc && acceptedS && acceptedS.length > 0 ? (
						<RequestCardList isSent={true} requests={acceptedS} />
					) : (
						<NoDataCard
							title={strings.collection.requests.sent.noAcceptedTitle}
							subtitle={strings.collection.requests.sent.noAcceptedSubtitle}
						/>
					)}
				</Tab>
				<Tab eventKey='declined' title={strings.collection.requests.declinedTitle}>
					{declinedSSucc && declinedS && declinedS.length > 0 ? (
						<RequestCardList isSent={true} requests={declinedS} />
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
