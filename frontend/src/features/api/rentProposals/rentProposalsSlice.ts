import { BaseApiSlice } from '../baseApiSlice';
import {
	RentProposal,
	ListRentProposalsParameters,
	CreateRentProposalParameters,
	UpdateRentProposalParameters
} from './types';

const RentProposalsApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		findProposal: build.query<RentProposal, URL>({
			query: (url) => url.toString()
		}),

		listProposals: build.query<RentProposal[], ListRentProposalsParameters>({
			query: ({ userId, page }) => `reviews?toUser=${userId}${page != null ? `&page=${page}` : ''}`
		}),

		createProposal: build.mutation<RentProposal, CreateRentProposalParameters>({
			query: (args) => ({
				url: 'proposals',
				method: 'POST',
				body: args
			})
		}),

		updateProposal: build.mutation<void, UpdateRentProposalParameters>({
			query: ({ url, ...args }) => ({
				url: url.toString(),
				method: 'PUT',
				body: args
			})
		})
	})
});

export const {
	useListProposalsQuery: useListRentProposals,
	useFindProposalQuery: useFindRentProposal,
	useCreateProposalMutation: useCreateRentProposal,
	useUpdateProposalMutation: useUpdateRentProposal
} = RentProposalsApiSlice;
