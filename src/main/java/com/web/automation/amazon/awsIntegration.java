package com.web.automation.amazon;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.*;
import org.apache.commons.lang.StringUtils;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public class awsIntegration {
	private static final int WAIT_FOR_TRANSITION_INTERVAL = 5000;
	private Properties config_prop;
	AmazonEC2 amazonEC2Client;
	AWSCredentials credentials;
	final String userData="";

	public awsIntegration(){
		config_prop = new Properties();
		try{
			FileReader reader;
			reader = new FileReader("config.properties");
			config_prop.load(reader);
		}
		catch (IOException e) {
			System.out.println("Failed to fetch/read 'config.properties' file.");
		}
		credentials = new BasicAWSCredentials(config_prop.getProperty("amazonAccessKey"), config_prop.getProperty("amazonSecretKey"));
		amazonEC2Client = new AmazonEC2Client(credentials);
		amazonEC2Client.setEndpoint(config_prop.getProperty("amazonEndPoint"));
	}

	/**
	 * @author Cigniti
	 * @param instanceId
	 * @return
	 */
	public String getInstancePublicIP(String instanceId) {
		DescribeInstancesResult describeInstancesRequest = amazonEC2Client.describeInstances();
		List<Reservation> reservations = describeInstancesRequest.getReservations();
		Set<Instance> allInstances = new HashSet<Instance>();
		for (Reservation reservation : reservations) {
			for (Instance instance : reservation.getInstances()) {
				if (instance.getInstanceId().equals(instanceId))
					return instance.getPublicIpAddress();
			}
		}
		return null;
	}

	/**
	 * @author Cigniti
	 * @param instanceIds
	 * @return
	 */
	public List<String> getInstancePublicIPs(List<String> instanceIds) {
		List<String> instanceIPs = new ArrayList<String>();
		for (String instanceId : instanceIds){
			instanceIPs.add(getInstancePublicIP(instanceId));
		}
		return instanceIPs;
	}

	/**
	 * @author Cigniti
	 * @param instanceId
	 * @return
	 */
	public String getInstanceStatus(String instanceId) {
		DescribeInstancesResult describeInstancesRequest = amazonEC2Client.describeInstances();
		List<Reservation> reservations = describeInstancesRequest.getReservations();
		Set<Instance> allInstances = new HashSet<Instance>();
		for (Reservation reservation : reservations) {
			for (Instance instance : reservation.getInstances()) {
				if (instance.getInstanceId().equals(instanceId))
					return instance.getState().getName();
			}
		}
		return null;
	}

	/**
	 * @author Cigniti
	 * @description: start amazon service instance
	 * @param instanceId
	 * @return
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 * @throws InterruptedException
	 */
	public String startInstance(String instanceId)
			throws AmazonServiceException, AmazonClientException, InterruptedException
	{
		// Start the instance
		StartInstancesRequest startRequest = new StartInstancesRequest().withInstanceIds(instanceId);
		StartInstancesResult startResult = amazonEC2Client.startInstances(startRequest);
		List<InstanceStateChange> stateChangeList = startResult.getStartingInstances();
		System.out.println("Starting instance '" + instanceId + "':");

		// Wait for the instance to be stopped
		return waitForStateChange("running", instanceId, 600);
	}

	/**
	 * @author Cigniti
	 * @description: stop amazon service instance
	 * @param instanceId
	 * @param forceStop
	 * @return
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 * @throws InterruptedException
	 */
	public String stopInstance(String instanceId, final Boolean forceStop)
			throws AmazonServiceException, AmazonClientException, InterruptedException
	{
		// Stop the instance
		StopInstancesRequest stopRequest = new StopInstancesRequest().withInstanceIds(instanceId).withForce(forceStop);
		StopInstancesResult stopResult = amazonEC2Client.stopInstances(stopRequest);
		List<InstanceStateChange> stateChangeList = stopResult.getStoppingInstances();
		System.out.println("Stopping instance '" + instanceId + "':");

		// Wait for the instance to be stopped
		return waitForStateChange("stopped", instanceId, 600);
	}

	/**
	 * @author ashsih jain
	 * @description: kill all amazon service instances
	 * @param instanceIds
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 * @throws InterruptedException
	 */
	public void terminateInstances(List<String> instanceIds)
			throws AmazonServiceException, AmazonClientException, InterruptedException
	{
		// Terminate the instance
		//        StopInstancesRequest stopRequest = new StopInstancesRequest().withInstanceIds(instanceId).withForce(forceStop);
		TerminateInstancesRequest tr = new TerminateInstancesRequest(instanceIds);
		TerminateInstancesResult terminateResult = amazonEC2Client.terminateInstances(tr);
		List<InstanceStateChange> stateChangeList = terminateResult.getTerminatingInstances();
		System.out.println("Stopping instances '" + instanceIds.toString() + "':");
		// Wait for the instance to be stopped
		//        return waitForTransitionCompletion(stateChangeList, "terminated", instanceIds);
		//        return waitForStateChange("terminated", "", 600);
	}

	/**
	 * @author Cigniti
	 * @param desiredState
	 * @param instanceId
	 * @param timeout
	 * @return
	 * @throws InterruptedException
	 */
	public String waitForStateChange(String desiredState, String instanceId, int timeout)
			throws InterruptedException {
		getInstanceStatus(instanceId);

		return null;
	}

	/**
	 * @author Cigniti
	 * @description: launch amazon service instances
	 * @param instance_count
	 * @return
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 * @throws InterruptedException
	 */
	public List<String> launchInstances(int instance_count)
			throws AmazonServiceException, AmazonClientException, InterruptedException{
		RunInstancesRequest runInstancesRequest =
				new RunInstancesRequest();

		runInstancesRequest.withImageId("ami-"+config_prop.getProperty("amazonInstanceImageId"))
		.withInstanceType(config_prop.getProperty("amazonInstanceType"))
		.withMinCount(1)
		.withMaxCount(instance_count)
		.withKeyName(config_prop.getProperty("amazonInstanceKeyName"))
		.withSubnetId("")
		// Format for user data (AlphaNumeric) - "PHBvd2Vyc2hlbGw+CmNtZxcccxxxxxxxx" +"b3JcRG93bmxvYWRzXFNlbchdxxxxxxxxxJzaGVsbD4="
		.withUserData(userData)
		.withSecurityGroupIds(config_prop.getProperty("amazonGroupId"));
		RunInstancesResult runInstancesResult =
				amazonEC2Client.runInstances(runInstancesRequest);
		List<Instance> instances = runInstancesResult.getReservation().getInstances();
		List<String> instanceIds = new ArrayList<String>();
		for (Instance instance : instances) {
			instanceIds.add(instance.getInstanceId());
		}
		return instanceIds;
	}

	/**
	 * @author Cigniti
	 * @param stateChangeList
	 * @param desiredState
	 * @param instanceId
	 * @return
	 * @throws InterruptedException
	 */
	public final String waitForTransitionCompletion(List<InstanceStateChange> stateChangeList,
			final String desiredState, String instanceId)
					throws InterruptedException
	{
		Boolean transitionCompleted = false;
		InstanceStateChange stateChange = stateChangeList.get(0);
		String previousState = stateChange.getPreviousState().getName();
		String currentState = stateChange.getCurrentState().getName();
		String transitionReason = "";

		while (!transitionCompleted)
		{
			try
			{
				Instance instance = this.describeInstance(amazonEC2Client, instanceId);
				currentState = instance.getState().getName();
				if (previousState.equals(currentState))
				{
					System.out.println("... '" + instanceId + "' is still in state " + currentState + " ...");
				}
				else
				{
					System.out.println("... '" + instanceId + "' entered state " + currentState + " ...");
					transitionReason = instance.getStateTransitionReason();
				}
				previousState = currentState;

				if (currentState.equals(desiredState))
				{
					transitionCompleted = true;
				}
			}
			catch (AmazonServiceException ase)
			{
				System.out.println("Failed to describe instance '" + instanceId + "'!");
				throw ase;
			}
			// Sleep for WAIT_FOR_TRANSITION_INTERVAL seconds until transition has completed.
			if (!transitionCompleted)
			{
				Thread.sleep(WAIT_FOR_TRANSITION_INTERVAL);
			}
		}
		System.out.println("Transition of instance '" + instanceId + "' completed with state " + currentState
				+ " (" + (StringUtils.isEmpty(transitionReason) ? "Unknown transition reason" : transitionReason)
				+ ").");
		return currentState;
	}

	/**
	 * @author Cigniti
	 * @param instancebuilder
	 * @param instanceId
	 * @return
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 */
	public Instance describeInstance(AmazonEC2 instancebuilder, String instanceId)
			throws AmazonServiceException, AmazonClientException
	{
		DescribeInstancesRequest describeRequest = new DescribeInstancesRequest().withInstanceIds(instanceId);
		DescribeInstancesResult result = instancebuilder.describeInstances(describeRequest);

		for (Reservation reservation : result.getReservations())
		{
			for (Instance instance : reservation.getInstances())
			{
				if (instanceId.equals(instance.getInstanceId()))
				{
					return instance;
				}
			}
		}
		return null;
	}

	/**
	 * @author Cigniti
	 * @throws AmazonServiceException
	 * @throws AmazonClientException
	 * @throws InterruptedException
	 */
	public void terminateAllRunningInstances()
			throws AmazonServiceException, AmazonClientException, InterruptedException{
		List<String> instanceIds = new ArrayList<String>();
		DescribeInstancesRequest request = new DescribeInstancesRequest();
		List<String> valuesT1 = new ArrayList<String>();
		valuesT1.add(config_prop.getProperty("amazonInstanceKeyName"));
		Filter filter = new Filter("key-name", valuesT1);

		DescribeInstancesResult result = amazonEC2Client.describeInstances(request.withFilters(filter));

		List<Reservation> reservations = result.getReservations();

		for (Reservation reservation : reservations) {
			List<Instance> instances = reservation.getInstances();

			for (Instance instance : instances) {
				instanceIds.add(instance.getInstanceId());
			}
		}
		terminateInstances(instanceIds);
	}
}