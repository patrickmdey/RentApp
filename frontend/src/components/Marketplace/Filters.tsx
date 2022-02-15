import { Row, Badge, Button } from 'react-bootstrap';
import { GeoAltFill, PersonFill, Tag } from 'react-bootstrap-icons';
import { ListArticleParameters } from '../../api/articles/types';
import { useFindCategory } from '../../api/categories/categoriesSlice';
import { strings } from '../../i18n/i18n';
import { useFindUser } from '../../api/users/usersSlice';

function CategoryName(props: { id: number }) {
	const { data: category } = useFindCategory(`categories/${props.id}`);
	return <>{category && category.description}</>;
}

function UserName(props: { id: number }) {
	const { data: user } = useFindUser(`users/${props.id}`);
	return <>{user && user.firstName}</>;
}

function Filters(props: { filters: ListArticleParameters; removeSearchParam: Function }) {
	const { filters, removeSearchParam } = props;

	return (
		<>
			{
				<Row className='mb-2'>
					{filters.name && filters.name.length > 0 && (
						<div className='d-flex align-items-center'>
							<span className='me-2 align-middle'>
								{strings.collection.filterInfo.search + ' ' + filters.name}
							</span>
						</div>
					)}

					{(filters.category || filters.user) && (
						<div className='d-flex align-items-center'>
							<p>{strings.collection.filterInfo.filtering}</p>

							{filters.category && (
								<Badge className='bg-color-secondary mx-2 d-flex align-items-center justify-content-center'>
									<p className='lead'>
										<Tag className='me-1' />
									</p>
									<p className='me-1'>
										<CategoryName id={filters.category} />
									</p>
									<Button
										variant='link'
										onClick={() => removeSearchParam('category')}
										className='text-light text-decoration-none p-0 m-0'
									>
										X
									</Button>
								</Badge>
							)}

							{filters.user && (
								<div className='badge bg-color-secondary mx-2 p-2'>
									<PersonFill />
									<p>
										<UserName id={filters.user} />
									</p>
									<Button
										variant='link'
										onClick={() => removeSearchParam('user')}
										className='text-light text-decoration-none p-0 m-0'
									>
										X
									</Button>
								</div>
							)}
						</div>
					)}

					{filters.location && (
						<div className='d-flex align-items-center'>
							<span className='me-2 align-middle'>
								{filters.location}
								<GeoAltFill />
							</span>
						</div>
					)}
				</Row>
			}
		</>
	);
}

export default Filters;
