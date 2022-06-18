import './ExpenseItem.css'

const ExpenseBadge = (props) => {

  const description = props.description

  return (
    <span className="expense-badge">{description}</span>
  )
}

export default ExpenseBadge