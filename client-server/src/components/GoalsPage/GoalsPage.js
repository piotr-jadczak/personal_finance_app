import { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css';
import { BsPlusSquare } from "react-icons/bs";
import SavingGoal from './SavingGoal/SavingGoal';
import SavingGoalForm from './SavingGoalForm/SavingGoalForm';

const GoalsPage = (props) => {

  const jwtToken = props.jwtToken
  const authHeader = {
    headers: { Authorization: `Bearer ${jwtToken}` }
  }
  const [savingGoals, setSavingGoals] = useState([])
  const [showForm, setShowForm] = useState(false)
  const handleClose = () => setShowForm(false)
  const handleShow = () => setShowForm(true)

  useEffect(() => {
    const getUserSavingGoals = async () => {
      const expGoals = await fetchSavingGoals()
      setSavingGoals(expGoals.data)
    }
    getUserSavingGoals()
  }, [])

  const onAdd = async (savingGoal) => {
    const newGoal = await addSavingGoal(savingGoal)
    const newGoalData = newGoal.data
    handleClose()
    setSavingGoals([...savingGoals, newGoalData])
  }

  const onDelete = async (id) => {
    const deleteResponse = await deleteSavingGoal(id)
    if(deleteResponse.data == true) {
      setSavingGoals(savingGoals.filter((s) => s.id !== id))
    }
    else {
      alert('Error while deleting saving goal')
    }
  }

  //fetch saving goals
  const fetchSavingGoals = () => {
    return axios.get("/saving-goals",authHeader)
  }
  //add saving goal
  const addSavingGoal = (goal) => {
    return axios.post("/saving-goals", goal, authHeader)
  }
  //delete saving goal
  const deleteSavingGoal = async(id) => {
    return axios.delete(`/saving-goals/${id}`, authHeader)
  }

  return (
    <div className="p-2">
      <div className="card">
        <div className="card-header">
          Goals {"  "}
          <BsPlusSquare size={24} style={{ color: 'green'}} onClick={handleShow} />
        </div>
        {savingGoals.map(s => <SavingGoal goal={s} key={s.id} onDelete={onDelete} />
        )}
      </div>
      {showForm && <SavingGoalForm 
                      onAdd={onAdd} 
                      showFrom={handleShow}
                      handleClose={handleClose}
                  />}
    </div>
  )
}

export default GoalsPage