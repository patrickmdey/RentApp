import { Button, Col, Form, Modal, Row } from 'react-bootstrap';
import { useForm } from 'react-hook-form';
import { useCreateRentProposal } from '../../api/rentProposals/rentProposalsSlice';
import { strings } from '../../i18n/i18n';
import FormInput from '../FormInputs/FormInput';
import useUserId from '../../hooks/useUserId';
import { useEffect } from 'react';
import { useNavigate } from 'react-router';
import LoadingComponent from '../LoadingComponent';

interface RequestFormT {
	message: string;
	startDate: Date;
	endDate: Date;
	articleId: number;
	renterId: number;
}

function RequestForm(props: { articleId: number }) {
	const { articleId } = props;
	const {
		register,
		handleSubmit,
		formState: { errors },
		getValues
	} = useForm<RequestFormT>({
		defaultValues: {
			message: '',
			startDate: undefined,
			endDate: undefined,
			articleId: articleId,
			renterId: undefined
		}
	});

	const loggedUser = useUserId();

	const navigate = useNavigate();

	const [createRequest, result] = useCreateRentProposal();

	useEffect(() => {
		if (result.isSuccess) navigate('/success');
	}, [result]);

	function onSubmit(data: RequestFormT) {
		createRequest({
			...data,
			startDate: data.startDate.toString(),
			endDate: data.endDate.toString(),
			renterId: loggedUser
		});
	}

	return (
		<div className='modal-content bg-color-grey'>
			<Form onSubmit={handleSubmit(onSubmit)}>
				{result.isLoading ? (
					<div className='mb-5'>
						<LoadingComponent />
					</div>
				) : (
					<>
						<Modal.Body className='bg-color-grey'>
							<Row>
								<Col md={6} lg={6} className='my-2'>
									<FormInput
										register={register}
										label={strings.collection.article.requestArticle.startDate}
										name='startDate'
										type='date'
										validation={{ required: true }}
										error={errors.startDate}
										errorMessage={strings.collection.article.requestArticle.errors.startDate}
									/>
								</Col>
								<Col md={6} lg={6} className='my-2'>
									<FormInput
										register={register}
										label={strings.collection.article.requestArticle.endDate}
										name='endDate'
										type='date'
										validation={{
											required: true
										}} //TODO: aca validate: isValidDate
										error={errors.startDate}
										errorMessage={strings.collection.article.requestArticle.errors.endDate}
									/>
								</Col>
							</Row>
							<FormInput
								register={register}
								label={strings.collection.article.requestArticle.message}
								placeholder={strings.collection.article.requestArticle.messagePlaceHolder}
								name='message'
								type='text'
								as='textarea'
								validation={{
									required: true,
									minLength: 10,
									maxLength: 310,
									validate: () => getValues('startDate') < getValues('endDate')
								}}
								error={errors.message}
								errorMessage={strings.collection.article.requestArticle.errors.message}
							/>
						</Modal.Body>
						<Modal.Footer>
							<Button type='submit' className='bg-color-action color-grey'>
								{strings.collection.article.requestArticle.send}
							</Button>
						</Modal.Footer>
					</>
				)}
			</Form>
		</div>
	);
}

export default RequestForm;
