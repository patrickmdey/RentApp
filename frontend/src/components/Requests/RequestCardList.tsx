import RequestCard from './RequestCard';
import { RentProposal } from '../../features/api/rentProposals/types';
function RequestCardList(props: { requests: RentProposal[] }) {
	const { requests } = props;
	return (
		<>
			{requests.map((req, i) => (
				<RequestCard key={i} {...req}></RequestCard>
			))}
		</>
	);
}

export default RequestCardList;
