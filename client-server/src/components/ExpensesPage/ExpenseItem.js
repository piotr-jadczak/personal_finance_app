import ExpenseDate from './ExpenseDate'
import './ExpenseItem.css'
import 'bootstrap/dist/css/bootstrap.css';
import ExpenseBadge from './ExpenseBadge';
import { BsXSquare } from 'react-icons/bs'

const ExpenseItem = (props) => {

  const date = new Date(props.expense.date);
  const amount = props.expense.amount;
  const note = props.expense.note;
  const categories = props.expense.categories;
  const id = props.expense.id;

  const onDeleteButtonClick = () => {
    props.onDelete(id)
  }

  return (
    <div className="row m-2 p-2">
      <div className="col-2"></div>
      <div className="col-1 bg-light py-2">
        <ExpenseDate date={date} />
      </div>
      <div className="col-5 bg-light py-2">
        <div className='expense-description'>
          <div className='expense-note'>
            {note} 
          </div>
          <div className='expense-amount'>{amount + " zł"}</div>
          <div className='expense-categories'>
            {categories.map(c => <ExpenseBadge description={c.name} key={c.id} />
            )}
          </div>    
        </div>
      </div>
      <div className="col-1 bg-light py-2">
        <div className='expense-button-holder'>
          <BsXSquare size={24} className='expense-delete-button' onClick={onDeleteButtonClick} />
        </div>   
      </div>
    </div>
  )
}

export default ExpenseItem