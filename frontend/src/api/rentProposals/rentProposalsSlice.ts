import { BaseApiSlice } from '../baseApiSlice';
import {
	RentProposal,
	ListRentProposalsParameters,
	CreateRentProposalParameters,
	UpdateRentProposalParameters
} from './types';
import paginatedResponse, { PaginatedData } from '../paginatedResponse';

const RentProposalsApiSlice = BaseApiSlice.injectEndpoints({
	endpoints: (build) => ({
		findProposal: build.query<RentProposal, string>({
			query: (url) => url.toString(),
			providesTags: (result) => (result ? [{ type: 'Proposal', id: result.id }] : ['Proposal'])
		}),

		listProposals: build.query<PaginatedData<RentProposal[]>, ListRentProposalsParameters>({
			query: ({ userId, page, type, state }) =>
				`proposals/${type}?user=${userId}&state=${state}${page != null ? `&page=${page}` : ''}`,
			transformResponse: (response: RentProposal[], meta) => paginatedResponse(response, meta),
			providesTags: (result) =>
				result && result.data
					? [
							...result.data.map(({ id }) => ({ type: 'Proposals' as const, id: id })),
							{ type: 'Proposal', id: 'PARTIAL-LIST' }
					  ]
					: [{ type: 'Proposal', id: 'PARTIAL-LIST' }],

			keepUnusedDataFor: 5
		}),

		createProposal: build.mutation<RentProposal, CreateRentProposalParameters>({
			query: (args) => ({
				url: 'proposals',
				method: 'POST',
				body: args
			}),
			invalidatesTags: [{ type: 'Proposal', id: 'PARTIAL-LIST' }]
		}),

		updateProposal: build.mutation<void, UpdateRentProposalParameters>({
			query: ({ url, ...args }) => ({
				url: url.toString(),
				method: 'PUT',
				body: args
			}),
			invalidatesTags: (_, __, arg) => {
				const parts = arg.url.split('/');
				return [{ type: 'Proposal', id: parts[parts.length - 1] }];
			}
		})
	})
});

export const {
	useListProposalsQuery: useListRentProposals,
	useFindProposalQuery: useFindRentProposal,
	useCreateProposalMutation: useCreateRentProposal,
	useUpdateProposalMutation: useUpdateRentProposal
} = RentProposalsApiSlice;
