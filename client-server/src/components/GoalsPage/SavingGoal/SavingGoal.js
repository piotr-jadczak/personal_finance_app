import { BsXSquare } from 'react-icons/bs'
import 'bootstrap/dist/css/bootstrap.css';
import './SavingGoal.css'
import ProgressBar from 'react-bootstrap/ProgressBar'

const SavingGoal = (props) => {

  const id = props.goal.id
  const amountToSave = props.goal.amountToSave
  const currentlySaved = props.goal.currentlySaved
  const percentCompleted = props.goal.percentCompleted
  const description = props.goal.description

  const onDeleteButtonClick = () => {
    props.onDelete(id)
  }

  return (
    <div className='goal-wrapper'>
      <div className='goal-button'><BsXSquare size={24} onClick={onDeleteButtonClick} fill='red' /></div> 
      <div className='goal-description'>Goal: {description}</div>
      <div className='goal-progress'>Saved: {currentlySaved + "zł / " + amountToSave + "zł"}</div>
      <ProgressBar variant="success" now={percentCompleted} label={`${percentCompleted}%`} />
    </div>
  )
}

export default SavingGoal