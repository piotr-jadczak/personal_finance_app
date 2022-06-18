import 'bootstrap/dist/css/bootstrap.css';
import { useState } from 'react';
import Select from 'react-select';
import { Modal } from "react-bootstrap";

const ExpenseForm = (props) => {

  const [date, setDate] = useState("2022-01-01")
  const [amount, setAmount] = useState(1)
  const [note, setNote] = useState("")
  const [expCategories, setExpCategories] = useState([])
  const allCategories = props.expCategories

  const onSubmit = (e) => {
    e.preventDefault()
    const newExpense = {
      date : date,
      amount : amount,
      note : note,
      categories : expCategories
    }
    props.onAdd(newExpense)
  }




  return (
    <Modal
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
      show={props.showFrom}
      onHide={props.handleClose}
     > 
      <Modal.Header className="text-center" closeButton>
        <Modal.Title>Add expense</Modal.Title>
      </Modal.Header>
     <Modal.Body>
       <div className='d-flex justify-content-center'>
        <form onSubmit={onSubmit} >
          <div className="form-group">
           <label className="form-label">Date</label>
           <input 
            type="date"
            className="form-control"
            required={true}
            value={date}
            min={"2018-01-01"}
            onChange={(e) => setDate(e.target.value)} 
          />
         </div>
         <div className="form-group">
           <label className="form-label">Amount</label>
           <input 
            type="number"
            className="form-control"
            step={0.01}
            required={true}
            value={amount}
            min={0.01}
            max={1000000}
            onChange={(e) => setAmount(e.target.value)} 
          />
         </div>
         <div className="form-group">
           <label className="form-label">Note</label>
           <input 
            type="text"
            className="form-control"
            required={true}
            onChange={(e) => setNote(e.target.value)} 
          />
         </div>
         <div className="form-group">
           <label className="form-label">Categories</label>
           <Select 
              className="form-control"
              value={expCategories} 
              onChange={(option) => setExpCategories(option)}
              options={allCategories}
              isMulti
              getOptionLabel={(option) => (option.name)}
              getOptionValue={(option) => option}
              isSearchable={true}
            >
           </Select>
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

export default ExpenseForm