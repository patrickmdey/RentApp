import { skipToken } from '@reduxjs/toolkit/dist/query';
import { useState } from 'react';
import { Card, Tabs, Tab } from 'react-bootstrap';
import { useListRentProposals } from '../../features/api/rentProposals/rentProposalsSlice';
import useUserId from '../../hooks/useUserId';
import { strings } from '../../i18n/i18n';
import { RECEIVED_STRING, states } from '../../pages/Request';
import NoDataCard from '../NoData/NoDataCard';
import RequestCardList from './RequestCardList';

function ReceivedRequestList() {
	const id = useUserId();

	const [key, setKey] = useState('pending');

	const { data: pendingR, isSuccess: pendingRSucc } = useListRentProposals(
		id !== null
			? {
					userId: id,
					type: RECEIVED_STRING,
					state: states.pending
			  }
			: skipToken
	);

	const { data: acceptedR, isSuccess: acceptedRSucc } = useListRentProposals(
		id !== null
			? {
					userId: id,
					type: RECEIVED_STRING,
					state: states.accepted
			  }
			: skipToken
	);

	const { data: declinedR, isSuccess: declinedRSucc } = useListRentProposals(
		id !== null
			? {
					userId: id,
					type: RECEIVED_STRING,
					state: states.declined
			  }
			: skipToken
	);

	return (
		<Card className='card-style min-height'>
			<Tabs activeKey={key} onSelect={(k) => k != null && setKey(k)}>
				<Tab eventKey='pending' title={strings.collection.requests.pendingTitle}>
					{pendingRSucc && pendingR && pendingR.length > 0 ? (
						<RequestCardList isSent={false} requests={pendingR} />
					) : (
						<NoDataCard
							title={strings.collection.requests.sent.noPendingTitle}
							subtitle={strings.collection.requests.sent.noPendingSubtitle}
						/>
					)}
				</Tab>
				<Tab eventKey='accepted' title={strings.collection.requests.acceptedTitle}>
					{acceptedRSucc && acceptedR && acceptedR.length > 0 ? (
						<RequestCardList isSent={false} requests={acceptedR} />
					) : (
						<NoDataCard
							title={strings.collection.requests.sent.noAcceptedTitle}
							subtitle={strings.collection.requests.sent.noAcceptedSubtitle}
						/>
					)}
				</Tab>
				<Tab eventKey='declined' title={strings.collection.requests.declinedTitle}>
					{declinedRSucc && declinedR && declinedR.length > 0 ? (
						<RequestCardList isSent={false} requests={declinedR} />
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
