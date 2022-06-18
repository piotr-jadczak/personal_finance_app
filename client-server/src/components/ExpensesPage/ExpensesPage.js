import './ExpensePage.css';
import 'bootstrap/dist/css/bootstrap.css';
import { useState, useEffect } from 'react';
import axios from 'axios';
import ExpenseItem from './ExpenseItem';
import { BsPlusSquare } from "react-icons/bs";
import ExpenseForm from './ExpenseForm/ExpenseForm';
import ExpenseFilter from './ExpenseFilter/ExpenseFilter';

const ExpensesPage = (props) => {

  const jwtToken = props.jwtToken
  const authHeader = {
    headers: { Authorization: `Bearer ${jwtToken}` }
  };
  const [expenseCategories, setExpenseCategories] = useState([])
  const [expenses, setExpenses] = useState([])
  const [showForm, setShowForm] = useState(false)
  const handleClose = () => setShowForm(false)
  const handleShow = () => setShowForm(true)

  const [startDate, setStartDate] = useState("2022-01-01")
  const [endDate, setEndDate] = useState("2022-12-31")

  useEffect(() => {
    const getExpenseCategories = async () => {
      const expCategories = await fetchCategories()
      setExpenseCategories(expCategories.data)
    }
    getExpenseCategories()
    onGet(startDate, endDate)
  }, [])

  const onDelete = async (id) => {
    const deleteResponse = await deleteExpense(id)
    if(deleteResponse.data == true) {
      setExpenses(expenses.filter((e) => e.id !== id))
    }
    else {
      alert('Error while deleting stock')
    }
  }
  
  const onAdd = async (expense) => {
    const newExpense = await addExpense(expense)
    const newExpenseData = newExpense.data
    handleClose()
    onGet(startDate, endDate)
  }

  const onGet = async (startDate, endDate) => {
    const exp = await fetchExpensesBetweenDates(startDate, endDate)
    const expData = exp.data
    setExpenses(expData)
  }

  //fetch expense categories
  const fetchCategories = () => {
    return axios.get("/all-expense-categories",authHeader)
  }
  //fetch expenses
  const fetchExpenses = () => {
    return axios.get("/expenses", authHeader)
  }
  //delete expense
  const deleteExpense = async(id) => {
    return axios.delete(`/expenses/${id}`, authHeader)
  }
  //add expense
  const addExpense = (expense) => {
    return axios.post("/expenses", expense, authHeader)
  }
  //fetch expenses between dates
  const fetchExpensesBetweenDates = (start, end) => {
    return axios.get(`/expenses/from=${start}/to=${end}`, authHeader)
  }

  return (
    <div className="p-2">
      {showForm && <ExpenseForm 
                expCategories={expenseCategories} 
                showFrom={handleShow}
                handleClose={handleClose}
                onAdd={onAdd}
            />}
      <div className="card">
        <div className="card-header">
          Expenses {"  "}
          <BsPlusSquare size={24} style={{ color: 'green'}} onClick={handleShow} />
        </div>
        <div className="card-body">
          <ExpenseFilter startDate={startDate} endDate={endDate} onGet={onGet} />
        </div>
      </div>
        {expenses.map(e => <ExpenseItem expense={e} key={e.id} onDelete={onDelete} />
        )}
    </div>
  )
}

export default ExpensesPage