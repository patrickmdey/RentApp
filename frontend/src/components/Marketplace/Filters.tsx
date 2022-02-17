import { Row, Badge, Button, Stack, Col } from 'react-bootstrap';
import { GeoAltFill, PersonFill, Tag, XCircle } from 'react-bootstrap-icons';
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
				<div>
					<Row>
						{filters.name && filters.name.length > 0 && (
							<div className='d-flex align-items-center'>
								<span className='me-2 align-middle'>
									{strings.collection.filterInfo.search}{' '}
									<Badge className='bg-color-secondary color-rentapp-black ms-2'>
										{filters.name}
									</Badge>
								</span>
							</div>
						)}
					</Row>
					<Row className='align-items-center justify-content-start my-2'>
						<Col md={7} lg={7} className='d-flex align-items-center'>
							{(filters.category || filters.user) && (
								<span className='me-2 align-middle'>
									{strings.collection.filterInfo.filtering}

									{filters.category && (
										<Badge className='bg-color-secondary justify-content-center align-items-center ms-2'>
											<Stack className='color-rentapp-black' direction='horizontal' gap={1}>
												<Tag />
												<CategoryName id={filters.category} />
												<XCircle onClick={() => removeSearchParam('category')} />
											</Stack>
										</Badge>
									)}

									{filters.user && (
										<Badge className='bg-color-secondary justify-content-center align-items-center ms-2'>
											<Stack className='color-rentapp-black' direction='horizontal' gap={1}>
												<PersonFill />
												<UserName id={filters.user} />
												<XCircle onClick={() => removeSearchParam('user')} />
											</Stack>
										</Badge>
									)}
								</span>
							)}
						</Col>
						<Col md={2} lg={2} />
						<Col md={3} lg={3}>
							{filters.location && (
								<div className='d-flex align-items-center justify-content-end'>
									<span className='me-2 align-middle'>
										{filters.location}
										<GeoAltFill />
									</span>
								</div>
							)}
						</Col>
					</Row>
				</div>
			}
		</>
	);
}

export default Filters;
