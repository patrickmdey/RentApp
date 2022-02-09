import RequestCard from './RequestCard';
import { RentProposal } from '../../features/api/rentProposals/types';
function RequestCardList(props: { requests: RentProposal[]; isSent: boolean }) {
	const { requests } = props;
	return (
		<>
			{requests.map((req, i) => (
				<RequestCard key={i} request={req} isSent={props.isSent}></RequestCard>
			))}
		</>
	);
}

export default RequestCardList;
