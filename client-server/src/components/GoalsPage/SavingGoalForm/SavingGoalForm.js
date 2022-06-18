import { Modal } from "react-bootstrap";
import { useState } from "react";
import 'bootstrap/dist/css/bootstrap.css';

const SavingGoalForm = (props) => {

  const [amountToSave, seTAmounToSave] = useState(100)
  const [description, setDescription] = useState("")

  const onSubmit = (e) => {
    e.preventDefault()
    const newSavingGoal = {
      amountToSave : amountToSave,
      description  : description
    }
    props.onAdd(newSavingGoal)
  }

  return (
    <Modal
      size="sm"
      aria-labelledby="contained-modal-title-vcenter"
      centered
      show={props.showFrom}
      onHide={props.handleClose}
     > 
      <Modal.Header className="text-center" closeButton>
        <Modal.Title>Add goal</Modal.Title>
      </Modal.Header>
     <Modal.Body>
       <div className='d-flex justify-content-center'>
         <form onSubmit={onSubmit} >
         <div className="form-group">
           <label className="form-label">Money to save</label>
           <input 
            type="number"
            className="form-control"
            step={0.01}
            required={true}
            value={amountToSave}
            min={10}
            max={100000000}
            onChange={(e) => seTAmounToSave(e.target.value)} 
          />
         </div>
         <div className="form-group">
           <label className="form-label">Description</label>
           <input 
            type="text"
            className="form-control"
            required={true}
            value={description}
            onChange={(e) => setDescription(e.target.value)} 
          />
         </div>
         <div className="form-group pt-2">
          <button type="submit" className="btn btn-dark form-button" >Submit</button>
         </div>
         </form>
       </div>
     </Modal.Body>
   </Modal>
  )
}

export default SavingGoalForm