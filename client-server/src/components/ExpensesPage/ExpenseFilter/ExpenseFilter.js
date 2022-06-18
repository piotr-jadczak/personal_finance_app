import { useState, useEffect } from 'react';
import './ExpenseFilter.css'
import { BsFunnelFill } from "react-icons/bs";

const ExpenseFilter = (props) => {

  const [startDate, setStartDate] = useState(props.startDate)
  const [endDate, setEndDate] = useState(props.endDate)

  const onSubmit = (e) => {
    e.preventDefault()
    props.onGet(startDate, endDate)
  }

  return (
    <div className="filter-wrapper">
      <BsFunnelFill size={24} style={{color : 'grey'}} />
      <form onSubmit={onSubmit} >
        <label className="filter-label" >Date from:</label>
        <input 
          type="date"
          required={true}
          value={startDate}
          min={"2022-01-01"}
          onChange={(e) => setStartDate(e.target.value)} 
        />
        <label className="filter-label" >Date to:</label>
        <input 
          type="date"
          required={true}
          value={endDate}
          min={"2022-01-01"}
          onChange={(e) => setEndDate(e.target.value)} 
          />
        <button type="submit" className="filter-button" >Filter</button>
      </form>
    </div>
  )
}

export default ExpenseFilter