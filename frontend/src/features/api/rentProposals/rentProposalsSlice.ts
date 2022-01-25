import { BaseApiSlice } from '../baseApiSlice';
import {
	RentProposal,
	ListRentProposalsParameters,
	CreateRentProposalParameters,
	UpdateRentProposalParameters
} from './types';

const RentProposalsApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		find: build.query<RentProposal, URL>({
			query: (url) => url.toString()
		}),

		list: build.query<RentProposal[], ListRentProposalsParameters>({
			query: ({ userId, page }) => `reviews?toUser=${userId}${page != null ? `&page=${page}` : ''}`
		}),

		create: build.mutation<RentProposal, CreateRentProposalParameters>({
			query: (args) => ({
				url: 'proposals',
				method: 'POST',
				body: args
			})
		}),

		update: build.mutation<void, UpdateRentProposalParameters>({
			query: ({ url, ...args }) => ({
				url: url.toString(),
				method: 'PUT',
				body: args
			})
		})
	})
});

export const {
	useListQuery: useListRentProposals,
	useFindQuery: useFindRentProposal,
	useCreateMutation: useCreateRentProposal,
	useUpdateMutation: useUpdateRentProposal
} = RentProposalsApiSlice;
